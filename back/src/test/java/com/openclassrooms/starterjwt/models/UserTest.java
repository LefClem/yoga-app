package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserTest {

    @Test
    public void equalsAndHashCode_shouldBeTrue_whenSameId() {
        User user = User.builder()
                .id(1L)
                .email("yoga@studio.com")
                .firstName("Test")
                .lastName("Test")
                .password("password")
                .build();
        User otherUser = User.builder()
                .id(1L)
                .email("notyoga@studio.com")
                .firstName("notTest")
                .lastName("notTest")
                .password("notPassword")
                .build();

        assertTrue(user.equals(otherUser));
        assertTrue(user.hashCode() == otherUser.hashCode());
    }

    @Test
    public void equalsAndHashCode_shouldBeFalse_whenDifferentId() {
        User user = User.builder()
                .id(1L)
                .email("yoga@studio.com")
                .firstName("Test")
                .lastName("Test")
                .password("password")
                .build();
        User otherUser = User.builder()
                .id(2L)
                .email("yoga@studio.com")
                .firstName("Test")
                .lastName("Test")
                .password("password")
                .build();

        assertFalse(user.equals(otherUser));
        assertFalse(user.hashCode() == otherUser.hashCode());
    }
}