package com.santhosh.platform.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void userCanLoginAndReadButCannotCreateCustomers() throws Exception {
        String token = login("user", "user-test-password");
        mvc.perform(get("/api/v1/customers").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
        mvc.perform(post("/api/v1/customers").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content("{\"fullName\":\"Test Customer\",\"email\":\"test@example.com\"}")).andExpect(status().isForbidden());
    }

    @Test
    void adminCanCreateCustomer() throws Exception {
        String token = login("admin", "admin-test-password");
        mvc.perform(post("/api/v1/customers").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content("{\"fullName\":\"Admin Customer\",\"email\":\"admin@example.com\"}")).andExpect(status().isCreated()).andExpect(jsonPath("$.email").value("admin@example.com"));
    }

    private String login(String username, String password) throws Exception {
        String response = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.accessToken").isNotEmpty()).andReturn().getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(response);
        return node.get("accessToken").asText();
    }
}