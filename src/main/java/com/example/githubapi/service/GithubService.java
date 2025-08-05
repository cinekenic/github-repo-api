package com.example.githubapi.service;

import com.example.githubapi.dto.github.GithubBranchDTO;
import com.example.githubapi.dto.github.GithubRepoDTO;
import com.example.githubapi.dto.github.GitHubRepositoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final RestClient restClient;

    public List<GitHubRepositoryDTO> getRepositories(String username) {
        try {
            List<GithubRepoDTO> repos = fetchRepos(username);

            return repos.stream()
                    .filter(repo -> !repo.fork())
                    .map(repo -> new GitHubRepositoryDTO(
                            repo.name(),
                            repo.owner().login(),
                            fetchBranches(username, repo.name()).stream()
                                    .map(branch -> new GitHubRepositoryDTO.Branch(
                                            branch.name(),
                                            branch.commit().sha()
                                    ))
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
        } catch (RestClientResponseException e) {
            handleError(e);
            return List.of();
        }
    }

    private List<GithubRepoDTO> fetchRepos(String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    private List<GithubBranchDTO> fetchBranches(String username, String repoName) {
        return restClient.get()
                .uri("/repos/{username}/{repo}/branches", username, repoName)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    private void handleError(RestClientResponseException e) {
        if (e.getRawStatusCode() == 404) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
