package com.example.githubapi.dto.github;

public record GithubRepoDTO(String name, Owner owner, boolean fork) {
    public record Owner(String login) {}
}