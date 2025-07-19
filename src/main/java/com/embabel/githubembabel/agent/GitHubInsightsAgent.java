package com.embabel.githubembabel.agent;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.AutoModelSelectionCriteria;
import com.embabel.common.ai.model.LlmOptions;
import com.embabel.githubembabel.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

@Agent(description = "Analyze GitHub repositories and provide comprehensive insights")
@Profile("!test")
public class GitHubInsightsAgent {

    @Autowired
    private GitHubService gitHubService;

    @AchievesGoal(description = "Comprehensive repository analysis with insights and recommendations")
    @Action
    public RepositoryInsights analyzeRepository(UserInput userInput, RepositoryData repositoryData, OperationContext context) {
        String analysis = context.promptRunner()
                .withLlm(LlmOptions.fromCriteria(AutoModelSelectionCriteria.INSTANCE))
                .withPromptContributor(Personas.ANALYST)
                .generateText(String.format("""
                Analyze this GitHub repository and provide comprehensive insights:

                Repository Information:
                - Name: %s
                - Description: %s
                - Primary Language: %s
                - Stars: %d
                - Forks: %d
                - Open Issues: %d
                - Size: %d KB
                - Last Updated: %s
                - License: %s
                - Default Branch: %s
                - Topics: %s
                - Has Wiki: %s
                - Has GitHub Pages: %s

                Please provide insights on:
                1. Repository health and activity
                2. Code quality indicators
                3. Community engagement
                4. Development patterns
                5. Technology stack assessment

                Keep the analysis concise but comprehensive (300-400 words).
                """,
                        repositoryData.name(),
                        repositoryData.description(),
                        repositoryData.language(),
                        repositoryData.stars(),
                        repositoryData.forks(),
                        repositoryData.openIssues(),
                        repositoryData.size(),
                        repositoryData.lastUpdate(),
                        repositoryData.license(),
                        repositoryData.defaultBranch(),
                        repositoryData.topics() != null ? String.join(", ", repositoryData.topics()) : "None",
                        repositoryData.hasWiki(),
                        repositoryData.hasPages()
                ).trim());

        String recommendations = context.promptRunner()
                .withLlm(LlmOptions.fromCriteria(AutoModelSelectionCriteria.INSTANCE))
                .withPromptContributor(Personas.ANALYST)
                .generateText(String.format("""
                Based on the repository data and previous analysis, provide specific recommendations:

                Repository: %s
                Current State: %d stars, %d forks, %d open issues
                Language: %s
                Last Updated: %s

                Provide actionable recommendations for:
                1. Improving repository visibility and adoption
                2. Code quality and maintenance suggestions
                3. Community building strategies
                4. Documentation improvements
                5. Security and best practices

                Keep recommendations practical and specific (200-300 words).
                """,
                        repositoryData.name(),
                        repositoryData.stars(),
                        repositoryData.forks(),
                        repositoryData.openIssues(),
                        repositoryData.language(),
                        repositoryData.lastUpdate()
                ).trim());

        return new RepositoryInsights(
                repositoryData,
                analysis,
                recommendations,
                Personas.ANALYST
        );
    }

    @Action
    public RepositoryData fetchRepositoryData(UserInput userInput) {
        String repoUrl = extractRepositoryUrl(userInput.getContent());
        if (repoUrl == null) {
            throw new IllegalArgumentException("Please provide a valid GitHub repository URL or owner/repo format (e.g., 'microsoft/vscode' or 'https://github.com/microsoft/vscode')");
        }

        return gitHubService.getRepositoryData(repoUrl);
    }

    private String extractRepositoryUrl(String input) {
        // Clean the input
        String cleaned = input.trim();
        
        // Handle GitHub URLs
        if (cleaned.contains("github.com/")) {
            // Extract owner/repo from URL
            String[] parts = cleaned.split("github.com/");
            if (parts.length > 1) {
                String ownerRepo = parts[1].split("/")[0] + "/" + parts[1].split("/")[1];
                return ownerRepo.replaceAll("[^a-zA-Z0-9/_-]", "");
            }
        }
        
        // Handle owner/repo format
        if (cleaned.matches("^[a-zA-Z0-9._-]+/[a-zA-Z0-9._-]+$")) {
            return cleaned;
        }
        
        // Try to extract from natural language
        String[] words = cleaned.split("\\s+");
        for (String word : words) {
            if (word.contains("/") && word.matches(".*[a-zA-Z0-9._-]+/[a-zA-Z0-9._-]+.*")) {
                return word.replaceAll("[^a-zA-Z0-9/_-]", "");
            }
        }
        
        return null;
    }
}
