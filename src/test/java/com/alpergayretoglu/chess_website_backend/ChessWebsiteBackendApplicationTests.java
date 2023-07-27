package com.alpergayretoglu.chess_website_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
class ChessWebsiteBackendApplicationTests {

    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) (new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("db")
            .withUsername("sa")
            .withPassword("sa"))
            .withReuse(true);

    @Test
    void contextLoads() {
    }

}
