package com.reinertisa.githubmcpclient;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

        @Bean
        CommandLineRunner commandLineRunner(List<McpSyncClient> mcpSyncClients, ConfigurableApplicationContext applicationContext) {
        return args -> {
            McpSyncClient fileSystem = mcpSyncClients.get(1);
            McpSchema.ListToolsResult listToolsResult = fileSystem.listTools();
            System.out.println(listToolsResult);
            McpSchema.CallToolResult listDirectory = fileSystem.callTool(new McpSchema.CallToolRequest("list_directory", Map.of("path", "/Users/administrator/Downloads")));
            System.out.println("List directory: ");
            System.out.println(listDirectory);

            McpSyncClient github = mcpSyncClients.get(0);
            McpSchema.ListToolsResult listToolsResult2 = github.listTools();
            System.out.println(listToolsResult2);
            McpSchema.CallToolRequest request = new McpSchema.CallToolRequest(
                    "list_commits",
                    Map.of(
                            "owner", "reinertisa",
                            "repo", "we-love-dogs",
                            "sha", "main",
                            "per_page", 10
                    )
            );
            McpSchema.CallToolResult listDirectory2 = github.callTool(request);
            System.out.println(listDirectory2);

            applicationContext.close();
        };
    }


}
