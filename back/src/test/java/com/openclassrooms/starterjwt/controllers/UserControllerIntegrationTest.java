package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    final User mockUser = User.builder()
            .id(1L)
            .email("usertest@mail.com")
            .firstName("User")
            .lastName("Test")
            .password(new BCryptPasswordEncoder().encode("test!1234"))
            .admin(false)
            .build();

    final User mockUser2 = User.builder()
            .id(1L)
            .email("usertest2@mail.com")
            .firstName("User")
            .lastName("Test")
            .password(new BCryptPasswordEncoder().encode("test!1234"))
            .admin(false)
            .build();

    @AfterEach
    void clear(){
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should retrieve correct user data of authenticated user")
    void testFindById_AuthorizedUser() throws Exception {
        Long mockUserId = userRepository.save(mockUser).getId();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/" + mockUserId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("User")));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should throw an error if user data is null")
    void testFindById_NotFoundUser() throws Exception {
        Long mockUserId = 67L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + mockUserId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Should have unauthorized response when getting user if unauthorized User")
    void testFindById_UnauthorizedResponse() throws Exception {
        Long mockUserId = userRepository.save(mockUser).getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + mockUserId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should throw bad request response if invalid id is passed")
    void testFindById_InvalidFormat() throws Exception {
        String invalidId = "invalidId";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + invalidId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser("usertest@mail.com")
    @DisplayName("Should delete auth user successfully ")
    void testDeleteUser_ResponseOK() throws Exception {
        Long mockUserId = userRepository.save(mockUser).getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/" + mockUserId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser("usertest2@mail.com")
    @DisplayName("Should not authorized delete request if unauthorized")
    void testDeleteUser_UnauthorizedUser() throws Exception {
        Long mockUserId = userRepository.save(mockUser).getId();
        userRepository.save(mockUser2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/" + mockUserId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser("usertest@mail.com")
    @DisplayName("Should throw bad request response if invalid id format when deleting user")
    void testDeleteUser_BadResponse() throws Exception {
        String invalidId = "Invalid_Id";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/" + invalidId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser("usertest2@mail.com")
    @DisplayName("Should respond with unauthorized if unauthorized user request to delete user")
    void testDeleteUser_NotFoundUser() throws Exception {
        Long mockUserId = 67L;
        userRepository.save(mockUser2);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/" + mockUserId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}


