# GitHub Repository API

This Spring Boot application exposes a REST API that integrates with the public [GitHub REST API](https://docs.github.com/en/rest).  
It returns a list of **non-forked repositories** for a given user, including their **branches** and the **SHA of the latest commit** on each branch.

## Technologies Used

- Java 21
- Spring Boot 3.5.4
- Gradle (via `./gradlew`)
- REST Client (`RestClient` from Spring 6)
- No WebFlux
- No DDD or Hexagonal architecture

## How to Run the Project

1. **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/github-repo-api.git
    cd github-repo-api
    ```
    2. **Build the project:**

    ```bash
    ./gradlew build
    ```

3. **Run the application:**

    ```bash
    ./gradlew bootRun
    ```
The application will start at:  
http://localhost:8080

---

## API Endpoint

### `GET /api/repos/{username}`

Returns a list of **non-forked repositories** for a given GitHub username.  
Each repository includes a list of branches with the **latest commit SHA**.


### Example Request

```json
[
  {
    "name": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "7fd1a60b01f91b314f59957b9da7c34d7e68fd64"
      }
    ]
  }
]