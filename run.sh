#!/usr/bin/env bash

# GitHub Embabel Agent Startup Script

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "🚀 Starting GitHub Embabel Agent..."
echo ""

# Check for Java
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed or not in PATH"
    echo "Please install Java 21 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "❌ Java 21 or higher is required (found Java $JAVA_VERSION)"
    exit 1
fi

echo "✅ Java version check passed"

# Check environment variables
echo ""
echo "🔍 Checking environment variables..."

GITHUB_TOKEN_SET=false
OPENAI_KEY_SET=false
ANTHROPIC_KEY_SET=false

if [ -n "${GITHUB_TOKEN:-}" ]; then
    echo "✅ GITHUB_TOKEN is set"
    GITHUB_TOKEN_SET=true
else
    echo "⚠️  GITHUB_TOKEN is not set (using anonymous GitHub API access - rate limited)"
fi

if [ -n "${OPENAI_API_KEY:-}" ]; then
    echo "✅ OPENAI_API_KEY is set"
    OPENAI_KEY_SET=true
else
    echo "❌ OPENAI_API_KEY is not set"
fi

if [ -n "${ANTHROPIC_API_KEY:-}" ]; then
    echo "✅ ANTHROPIC_API_KEY is set"
    ANTHROPIC_KEY_SET=true
else
    echo "❌ ANTHROPIC_API_KEY is not set"
fi

if [ "$OPENAI_KEY_SET" = false ] && [ "$ANTHROPIC_KEY_SET" = false ]; then
    echo ""
    echo "❌ ERROR: At least one LLM API key is required!"
    echo ""
    echo "Please set one of the following:"
    echo "  export OPENAI_API_KEY=your_openai_key"
    echo "  export ANTHROPIC_API_KEY=your_anthropic_key"
    echo ""
    echo "Get API keys from:"
    echo "  OpenAI: https://platform.openai.com/api-keys"
    echo "  Anthropic: https://www.anthropic.com/api"
    exit 1
fi

echo ""
echo "🔨 Building the application..."
cd "$SCRIPT_DIR"
./gradlew build -q

echo ""
echo "🎯 Starting the agent shell..."
echo ""
echo "Usage examples:"
echo "  x \"Analyze microsoft/vscode\""
echo "  x \"Give me insights on https://github.com/facebook/react\""
echo "  x \"What can you tell me about the spring-boot repository?\""
echo ""
echo "Type 'help' for more commands, 'exit' to quit"
echo ""

# Run the application
./gradlew bootRun -q
