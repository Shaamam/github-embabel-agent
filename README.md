# GitHub Embabel Agent

A Spring Boot application using the Embabel agent framework to analyze GitHub repositories and provide comprehensive insights.

## Features

- **Repository Analysis**: Fetches detailed information about GitHub repositories
- **AI-Powered Insights**: Uses LLM to analyze repository health, activity, and trends
- **Smart Recommendations**: Provides actionable suggestions for improvement
- **Multiple Input Formats**: Supports GitHub URLs, owner/repo format, or natural language queries
- **Interactive Shell**: Built-in shell interface for easy testing and interaction

## Prerequisites

- Java 21 or higher
- Gradle 8.10.2 (included via wrapper)
- GitHub API access (optional token for higher rate limits)
- OpenAI or Anthropic API key for LLM functionality

## Environment Setup

1. **GitHub API Token** (optional but recommended):
   ```bash
   export GITHUB_TOKEN=your_github_token_here
   ```

2. **LLM API Keys** (at least one required):
   ```bash
   export OPENAI_API_KEY=your_openai_key_here
   # OR
   export ANTHROPIC_API_KEY=your_anthropic_key_here
   ```

## Quick Start

1. **Build the project**:
   ```bash
   ./gradlew build
   ```

2. **Run the application**:
   ```bash
   ./gradlew bootRun
   ```

3. **Use the interactive shell**:
   ```
   shell:>x "Analyze microsoft/vscode"
   shell:>x "Give me insights on https://github.com/facebook/react"
   shell:>x "What can you tell me about the spring-boot repository?"
   ```

## Usage Examples

The agent can analyze repositories using various input formats:

### GitHub URLs
```
x "Analyze https://github.com/microsoft/vscode"
```

### Owner/Repository Format
```
x "Look at facebook/react"
```

### Natural Language
```
x "Tell me about the TypeScript repository"
x "Give me insights on the Kubernetes project"
```

## Sample Output

The agent provides comprehensive analysis including:

- Repository metadata (stars, forks, issues, language, etc.)
- Health and activity assessment
- Code quality indicators
- Community engagement metrics
- Actionable recommendations for improvement
- Security and best practices suggestions

## Project Structure

```
src/
├── main/java/com/embabel/github/
│   ├── GitHubInsightsApplication.java     # Main Spring Boot application
│   ├── agent/
│   │   └── GitHubInsightsAgent.java       # Main agent with analysis logic
│   ├── service/
│   │   └── GitHubService.java             # GitHub API integration
│   └── exception/
│       └── GitHubRepositoryException.java # Custom exception handling
└── test/java/com/embabel/github/agent/
    └── GitHubInsightsAgentTest.java       # Unit tests
```

## Configuration

Edit `src/main/resources/application.properties` to customize:

- GitHub API token
- Embabel model configurations
- Logging levels

## Development

### Running Tests
```bash
./gradlew test
```

### Building Distribution
```bash
./gradlew build
```

### Code Quality
The project includes:
- Comprehensive unit tests with Mockito
- SonarQube-compatible code quality checks
- Embabel agent testing framework integration

## API Integration

This agent integrates with:
- **GitHub API**: For repository data fetching
- **OpenAI/Anthropic**: For intelligent analysis and recommendations
- **Embabel Framework**: For agent orchestration and management

## Contributing

1. Follow the existing code patterns using Embabel annotations
2. Add comprehensive tests for new features
3. Update documentation for any new capabilities
4. Ensure all quality checks pass

## License

Copyright 2024-2025 Embabel Software, Inc.

Licensed under the Apache License, Version 2.0.
