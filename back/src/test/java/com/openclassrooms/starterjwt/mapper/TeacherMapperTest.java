package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TeacherMapperTest {

    @Autowired
    private TeacherMapper mapper;

    TeacherDto teacherDto;
    Teacher teacher;

    @BeforeEach
    public void init() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        teacherDto = new TeacherDto(1L, "Teacher", "Teacher", currentLocalDateTime, currentLocalDateTime);
        teacher = Teacher.builder()
                .id(1L)
                .firstName("Teacher")
                .lastName("Teacher")
                .createdAt(currentLocalDateTime)
                .updatedAt(currentLocalDateTime)
                .build();
    }

    @Test
    public void toEntity_shouldReturnNull_ifTeacherDtoNull() {
        TeacherDto nullTeacherDto = null;
        Teacher result = mapper.toEntity(nullTeacherDto);

        assertNull(result);
    }

    @Test
    public void shouldMapDtoToEntity() {
        Teacher result = mapper.toEntity(teacherDto);

        assert(result.equals(teacher));
        assert(result.getId().equals(teacher.getId()));
        assert(result.getFirstName().equals(teacher.getFirstName()));
        assert(result.getLastName().equals(teacher.getLastName()));
        assert(result.getCreatedAt().equals(teacher.getCreatedAt()));
        assert(result.getUpdatedAt().equals(teacher.getUpdatedAt()));
    }

    @Test
    public void toListEntity_shouldReturnNull_ifTeacherDtoListNull() {
        List<TeacherDto> nullTeacherDtoList = null;
        List<Teacher> result = mapper.toEntity(nullTeacherDtoList);

        assertNull(result);
    }

    @Test
    public void shouldMapListDtoToListEntity() {
        List<Teacher> result = mapper.toEntity(Collections.singletonList(teacherDto));

        assert(result.equals(Collections.singletonList(teacher)));
    }

    @Test
    public void toDto_shouldReturnNull_ifTeacherNull() {
        Teacher nullTeacher = null;
        TeacherDto result = mapper.toDto(nullTeacher);

        assertNull(result);
    }

    @Test
    public void shouldMapEntityToDto() {
        TeacherDto result = mapper.toDto(teacher);

        assert(result.equals(teacherDto));
        assert(result.getId().equals(teacherDto.getId()));
        assert(result.getFirstName().equals(teacherDto.getFirstName()));
        assert(result.getLastName().equals(teacherDto.getLastName()));
        assert(result.getCreatedAt().equals(teacherDto.getCreatedAt()));
        assert(result.getUpdatedAt().equals(teacherDto.getUpdatedAt()));
    }

    @Test
    public void toListDto_shouldReturnNull_ifTeacherListNull() {
        List<Teacher> nullTeacherList = null;
        List<TeacherDto> result = mapper.toDto(nullTeacherList);

        assertNull(result);
    }

    @Test
    public void shouldMapListEntityToListDto() {
        List<TeacherDto> result = mapper.toDto(Collections.singletonList(teacher));

        assert(result.equals(Collections.singletonList(teacherDto)));
    }
}