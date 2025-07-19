package com.embabel.githubembabel.service;

import com.embabel.github.exception.GitHubRepositoryException;
import com.embabel.githubembabel.agent.RepositoryData;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GitHubService {

    @Value("${github.token:}")
    private String githubToken;

    public RepositoryData getRepositoryData(String ownerRepo) {
        try {
            GitHub github = createGitHubClient();
            GHRepository repo = github.getRepository(ownerRepo);

            return new RepositoryData(
                    repo.getName(),
                    repo.getDescription(),
                    repo.getLanguage(),
                    repo.getStargazersCount(),
                    repo.getForksCount(),
                    repo.getOpenIssueCount(),
                    repo.getUpdatedAt().toInstant().atZone(java.time.ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    repo.getLicense() != null ? repo.getLicense().getName() : null,
                    repo.getSize(),
                    repo.hasWiki(),
                    repo.hasPages(),
                    repo.getDefaultBranch(),
                    repo.listTopics() != null ? repo.listTopics() : List.of()
            );

        } catch (IOException e) {
            throw new GitHubRepositoryException("Failed to fetch repository data for: " + ownerRepo, e);
        }
    }

    private GitHub createGitHubClient() throws IOException {
        if (githubToken != null && !githubToken.isEmpty()) {
            return new GitHubBuilder().withOAuthToken(githubToken).build();
        } else {
            // Use anonymous access (rate limited)
            return GitHub.connectAnonymously();
        }
    }
}
