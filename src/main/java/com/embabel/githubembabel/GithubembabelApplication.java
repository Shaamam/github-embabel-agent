package com.embabel.githubembabel;

import com.embabel.agent.config.annotation.EnableAgentShell;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.config.annotation.LoggingThemes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAgentShell
@EnableAgents(loggingTheme = LoggingThemes.STAR_WARS)
public class GithubembabelApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubembabelApplication.class, args);
    }

}
