package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServicesTest {
    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("Should find a user by id")
    void test_FindById_ResponseOk(){
        User mockUser = User.builder()
                .email("johndoe@mail.com")
                .firstName("John")
                .lastName("Doe")
                .password("test!1234")
                .admin(false)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User user = userService.findById(1L);

        assertThat(user).isEqualTo(mockUser);
        assertThat(user.isAdmin()).isEqualTo(false);
        verify(userRepository, times(1)).findById(1L);
    }

    void test_delete_ResponseOk(){
        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
