package com.example.githubapi.dto.github;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GitHubRepositoryDTO {
    private String name;
    private String ownerLogin;
    private List<Branch> branches;

    @Data
    @AllArgsConstructor
    public static class Branch {
        private String name;
        private String lastCommitSha;
    }
}
