package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ObjectMapper mapper;

    final Teacher mockTeacher = Teacher.builder()
            .firstName("Frodon")
            .lastName("Sacquet")
            .build();

    @AfterEach
    void clean() { teacherRepository.deleteAll(); }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should find a teacher by his id")
    void testFindbyId_ResponseOK() throws Exception {
        Long mockTeacherId = teacherRepository.save(mockTeacher).getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/" + mockTeacherId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should return not found when teacher id doesn't exist")
    void testFindById_NotFound() throws Exception {
        Long mockTeacherId = 67L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher" + mockTeacherId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should return an error when teacher id doesn't exist")
    void testFindById_Invalid() throws Exception {
        String invalidId = "invalidId";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/" + invalidId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should find all teacher")
    void testFindAll_responseOK() throws Exception {
        teacherRepository.save(mockTeacher);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
