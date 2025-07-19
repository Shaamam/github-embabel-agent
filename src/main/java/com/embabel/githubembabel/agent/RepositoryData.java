package com.embabel.githubembabel.agent;

public record RepositoryData(
        String name,
        String description,
        String language,
        int stars,
        int forks,
        int openIssues,
        String lastUpdate,
        String license,
        long size,
        boolean hasWiki,
        boolean hasPages,
        String defaultBranch,
        java.util.List<String> topics
) {}
