package com.embabel.githubembabel.agent;

import com.embabel.agent.domain.library.HasContent;
import com.embabel.agent.prompt.persona.Persona;
import com.embabel.common.core.types.Timestamped;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record RepositoryInsights(
        RepositoryData repository,
        String analysis,
        String recommendations,
        Persona analyst
) implements HasContent, Timestamped {

    @Override
    @NonNull
    public Instant getTimestamp() {
        return Instant.now();
    }

    @Override
    @NonNull
    public String getContent() {
        return String.format("""
            # Repository Analysis: %s
            
            ## Repository Details
            - **Name**: %s
            - **Description**: %s
            - **Primary Language**: %s
            - **Stars**: %d ⭐
            - **Forks**: %d 🍴
            - **Open Issues**: %d 📋
            - **Size**: %d KB
            - **Last Updated**: %s
            - **License**: %s
            - **Default Branch**: %s
            - **Topics**: %s
            - **Features**: %s%s

            ## Analysis
            %s

            ## Recommendations
            %s

            ---
            *Analysis by %s on %s*
            """,
                repository.name(),
                repository.name(),
                repository.description() != null ? repository.description() : "No description available",
                repository.language() != null ? repository.language() : "Not specified",
                repository.stars(),
                repository.forks(),
                repository.openIssues(),
                repository.size(),
                repository.lastUpdate(),
                repository.license() != null ? repository.license() : "No license specified",
                repository.defaultBranch(),
                repository.topics() != null ? String.join(", ", repository.topics()) : "None",
                repository.hasWiki() ? "Wiki " : "",
                repository.hasPages() ? "GitHub Pages " : "",
                analysis,
                recommendations,
                analyst.getName(),
                getTimestamp().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' HH:mm"))
        ).trim();
    }
}
