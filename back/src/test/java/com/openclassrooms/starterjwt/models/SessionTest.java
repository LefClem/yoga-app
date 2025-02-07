package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SessionTest {

    @Test
    public void equalsAndHashCode_shouldBeTrue_whenSameId() {
        Session session = Session.builder()
                .id(1L)
                .name("Session")
                .description("Session")
                .date(now())
                .build();
        Session otherSession = Session.builder()
                .id(1L)
                .name("AnotherSession")
                .description("AnotherSession")
                .date(now())
                .build();

        assertTrue(session.equals(otherSession));
        assertTrue(session.hashCode() == otherSession.hashCode());
    }

    @Test
    public void equalsAndHashCode_shouldBeFalse_whenDifferentId() {
        Session session = Session.builder()
                .id(1L)
                .name("Session")
                .description("Session")
                .date(now())
                .build();
        Session otherSession = Session.builder()
                .id(2L)
                .name("Session")
                .description("Session")
                .date(now())
                .build();


        assertFalse(session.equals(otherSession));
        assertFalse(session.hashCode() == otherSession.hashCode());
    }
}