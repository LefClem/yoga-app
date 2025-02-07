package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserDetailsImplTest {
    @Test
    public void equals_shouldBeTrue_whenSameId_OrSameObject() {
        UserDetails userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("user@gmail.com")
                .firstName("User")
                .lastName("User")
                .admin(true)
                .build();
        UserDetails otherUserDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("notUser@gmail.com")
                .firstName("Other User")
                .lastName("Other User")
                .admin(false)
                .build();

        assertTrue(userDetails.equals(userDetails));
        assertTrue(userDetails.equals(otherUserDetails));
        assertFalse(userDetails.getUsername().equals(otherUserDetails.getUsername()));
    }

    @Test
    public void equals_shouldBeFalse_whenDifferentId_OrNotSameObject() {
        UserDetails userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("user@gmail.com")
                .firstName("User")
                .lastName("User")
                .admin(true)
                .build();
        UserDetails otherUserDetails = UserDetailsImpl.builder()
                .id(2L)
                .username("user@gmail.com")
                .firstName("User")
                .lastName("User")
                .admin(true)
                .build();
        UserDetails nullUserDetails = null;
        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .build();

        assertFalse(userDetails.equals(nullUserDetails));
        assertFalse(userDetails.equals(teacher));
        assertFalse(userDetails.equals(otherUserDetails));
        assertTrue(userDetails.getUsername().equals(otherUserDetails.getUsername()));
    }
}