package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtUtilsTest {
    @Autowired
    private JwtUtils utils;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository.deleteAll();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User mockUser = User.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@gmail.com")
                .password(encoder.encode("password"))
                .admin(false)
                .build();

        userRepository.save(mockUser);
    }

    @Test
    public void getUserNameFromJwtToken_shouldReturnUsername() {
        String expectedUsername = "test@gmail.com";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(expectedUsername,"password");
        Authentication authentication = authManager.authenticate(authRequest);
        String token = utils.generateJwtToken(authentication);

        String result = utils.getUserNameFromJwtToken(token);

        assert(expectedUsername.equals(result));
    }

    @Test
    public void validateJwtToken_shouldReturnTrue_whenValidToken() {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("test@gmail.com","password");
        Authentication authentication = authManager.authenticate(authRequest);
        String token = utils.generateJwtToken(authentication);

        assertTrue(utils.validateJwtToken(token));
    }

    @Test
    public void validateJwtToken_shouldReturnFalse_whenInvalidToken() {
        String token = "invalid";

        assertFalse(utils.validateJwtToken(token));
    }
}