package com.example.githubapi.dto.github;

public record GithubBranchDTO(String name, Commit commit) {
    public record Commit(String sha) {}
}