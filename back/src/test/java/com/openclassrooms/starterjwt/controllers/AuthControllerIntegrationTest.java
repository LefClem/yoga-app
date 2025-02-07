package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    final User mockUser = User.builder()
            .id(1L)
            .email("usertest@mail.com")
            .firstName("User")
            .lastName("Test")
            .password(new BCryptPasswordEncoder().encode("test!1234"))
            .admin(false)
            .build();

    @BeforeEach
    void init(){
        userRepository.deleteAll();
        userRepository.save(mockUser);
    }

    @AfterEach
    void clear(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should log a user with valid credentials")
    void testLogin_ResponseOK() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .email("usertest@mail.com")
                .password("test!1234")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not log the user if credentials are wrong")
    void testLogin_ResponseUnauthorized() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .email("wrong@test.com")
                .password("wrong")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Should register new user")
    void testRegister_ResponseOK() throws Exception {
        SignupRequest request = SignupRequest.builder()
                .firstName("Marco")
                .lastName("Polo")
                .email("marco@polo.com")
                .password("test!1234")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(userRepository.findByEmail("marco@polo.com").isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should return an error when email already exist in database")
    void testRegisterFail() throws Exception {
        SignupRequest request = SignupRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@mail.com")
                .password("test")
                .build();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("")));
    }


}
