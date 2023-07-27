package com.alpergayretoglu.chess_website_backend.controller;

import com.alpergayretoglu.chess_website_backend.security.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = {"/testData.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = {"/truncateAllTables.sql", "/import.sql"})
public abstract class BaseIntegrationTest {

    @Autowired
    public JwtService jwtService;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders ADMIN_HEADERS;

    @BeforeAll
    public void init() {
        ADMIN_HEADERS = generateJwtHeadersFor("cdf8d686-3bbb-4a64-a8ee-dd24df45e7d9");
    }

    public <REQ, RES> ResponseEntity<RES> sendRequest(String url, HttpMethod method, REQ requestBody, ParameterizedTypeReference<RES> responseType) {
        final JsonNode requestBodyJson = objectMapper.valueToTree(requestBody);
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, ADMIN_HEADERS);
        return restTemplate.exchange(
                url,
                method,
                request,
                responseType
        );
    }

    public <REQ, RES> ResponseEntity<RES> sendRequestAs(String asUserWithId, String url, HttpMethod method, REQ requestBody, ParameterizedTypeReference<RES> responseType) {
        final HttpHeaders headers = generateJwtHeadersFor(asUserWithId);
        final JsonNode requestBodyJson = objectMapper.valueToTree(requestBody);
        final HttpEntity<JsonNode> request = new HttpEntity<>(requestBodyJson, headers);
        return restTemplate.exchange(
                url,
                method,
                request,
                responseType
        );
    }

    private HttpHeaders generateJwtHeadersFor(String userId) {
        String token = jwtService.createToken(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

}
