package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TeacherTest {

    @Test
    public void equalsAndHashCode_shouldBeTrue_whenSameId() {
        Teacher teacher = Teacher.builder().id(1L).firstName("Test").build();
        Teacher otherTeacher = Teacher.builder().id(1L).firstName("NotTest").build();

        assertTrue(teacher.equals(otherTeacher));
        assertTrue(teacher.hashCode() == otherTeacher.hashCode());
    }

    @Test
    public void equalsAndHashCode_shouldBeFalse_whenDifferentId() {
        Teacher teacher = Teacher.builder().id(1L).firstName("Test").build();
        Teacher otherTeacher = Teacher.builder().id(2L).firstName("Test").build();

        assertFalse(teacher.equals(otherTeacher));
        assertFalse(teacher.hashCode() == otherTeacher.hashCode());
    }
}