package com.example.githubapi.controller;

import com.example.githubapi.dto.github.GitHubRepositoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubControllerIntegrationTest {

@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnRealRepositoriesWithBranchesFromGitHub() {
        // given
        String username = "octocat";
        String url = "http://localhost:" + port + "/api/repos/" + username;

        // when
        ResponseEntity<GitHubRepositoryDTO[]> response = restTemplate.getForEntity(url, GitHubRepositoryDTO[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        GitHubRepositoryDTO[] repos = response.getBody();

        assertThat(repos).isNotNull();
        assertThat(repos.length).isGreaterThan(0);

        GitHubRepositoryDTO repo = repos[0];
        assertThat(repo.getName()).isNotBlank();
        assertThat(repo.getOwnerLogin()).isEqualTo("octocat");

        List<GitHubRepositoryDTO.Branch> branches = repo.getBranches();
        assertThat(branches).isNotNull();
        assertThat(branches.size()).isGreaterThan(0);

        GitHubRepositoryDTO.Branch branch = branches.get(0);
        assertThat(branch.getName()).isNotBlank();
        assertThat(branch.getLastCommitSha()).isNotBlank();
    }
}
