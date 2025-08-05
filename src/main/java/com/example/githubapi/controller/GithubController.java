package com.example.githubapi.controller;

import jakarta.validation.constraints.NotBlank;
import com.example.githubapi.dto.github.GitHubRepositoryDTO;
import com.example.githubapi.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/repos")
@Validated
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping("/{username}")
    public ResponseEntity<List<GitHubRepositoryDTO>> getRepositories(@PathVariable @NotBlank String username) {
        List<GitHubRepositoryDTO> response = githubService.getRepositories(username);
        return ResponseEntity.ok(response);
    }
}