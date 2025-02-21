package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TeacherServicesTest {
    @MockBean
    TeacherRepository teacherRepository;

    @Autowired
    TeacherService teacherService;

    final Teacher mockTeacher1 = Teacher.builder()
            .firstName("Minerva")
            .lastName("Mcgonagall")
            .build();

    final Teacher mockTeacher2 = Teacher.builder()
            .firstName("Albus")
            .lastName("Dumbeldore")
            .build();

    final List<Teacher> teachersListMock = Arrays.asList(mockTeacher1, mockTeacher2);

    @Test
    @DisplayName("Should return the list of teachers")
    void test_FindAllTeachers(){
        when(teacherRepository.findAll()).thenReturn(teachersListMock);

        List<Teacher> teacherList = teacherService.findAll();

        assertThat(teacherList).isEqualTo(teachersListMock);
        assertThat(teachersListMock.size()).isEqualTo(2);
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return the teacher by his id")
    void test_FindTeacherById_ResponseOK(){
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teachersListMock.get(0)));

        Teacher mockTeacher = teacherService.findById(1L);

        assertThat(mockTeacher).isEqualTo(mockTeacher1);
        assertThat(mockTeacher.getFirstName()).isEqualTo("Minerva");
        verify(teacherRepository, times(1)).findById(1L);
    }
}
