package com.example.githubapi.service;

import com.example.githubapi.dto.github.GitHubRepositoryDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GithubServiceIntegrationTest {

    private static GithubService githubService;

    @BeforeAll
    static void setup() {
        // prawdziwe GitHub API
        RestClient restClient = RestClient.builder()
                .baseUrl("https://api.github.com")
                .build();

        githubService = new GithubService(restClient);
    }

    @Test
    void shouldReturnRealRepositoriesWithBranchesForKnownUser() {
        // given
        String username = "octocat"; // publiczne konto GitHub

        // when
        List<GitHubRepositoryDTO> repositories = githubService.getRepositories(username);

        // then
        assertThat(repositories).isNotEmpty();

        GitHubRepositoryDTO repo = repositories.get(0);
        assertThat(repo.getName()).isNotBlank();
        assertThat(repo.getOwnerLogin()).isEqualTo("octocat");

        List<GitHubRepositoryDTO.Branch> branches = repo.getBranches();
        assertThat(branches).isNotEmpty();

        GitHubRepositoryDTO.Branch branch = branches.get(0);
        assertThat(branch.getName()).isNotBlank();
        assertThat(branch.getLastCommitSha()).isNotBlank();
    }
}
