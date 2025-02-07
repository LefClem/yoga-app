package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SessionMapperTest {

    @Autowired
    private SessionMapper mapper;

    SessionDto sessionDto;
    Session session;

    @BeforeEach
    public void init() {
        Date currentDate = now();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        Teacher teacher = Teacher.builder().id(1L).firstName("test").lastName("test").build();

        sessionDto = new SessionDto(1L, "Session", currentDate, 1L, "Test description", new ArrayList<>(), currentLocalDateTime, currentLocalDateTime);
        session = Session.builder()
                .id(1L)
                .name("Session")
                .date(currentDate)
                .teacher(teacher)
                .description("Test description")
                .users(new ArrayList<>())
                .createdAt(currentLocalDateTime)
                .updatedAt(currentLocalDateTime)
                .build();
    }

    @Test
    public void toEntity_shouldReturnNull_ifSessionDtoNull() {
        SessionDto nullSessionDto = null;
        Session result = mapper.toEntity(nullSessionDto);

        assertNull(result);
    }

    @Test
    public void shouldMapDtoToEntity() {
        Session result = mapper.toEntity(sessionDto);

        assert(result.equals(session));
        assert(result.getId().equals(session.getId()));
        assert(result.getName().equals(session.getName()));
        assert(result.getDate().equals(session.getDate()));
        assert(result.getDescription().equals(session.getDescription()));
        assert(result.getUsers().equals(session.getUsers()));
        assert(result.getCreatedAt().equals(session.getCreatedAt()));
        assert(result.getUpdatedAt().equals(session.getUpdatedAt()));
    }

    @Test
    public void toListEntity_shouldReturnNull_ifSessionDtoListNull() {
        List<SessionDto> nullSessionDtoList = null;
        List<Session> result = mapper.toEntity(nullSessionDtoList);

        assertNull(result);
    }

    @Test
    public void shouldMapListDtoToListEntity() {
        List<Session> result = mapper.toEntity(Collections.singletonList(sessionDto));

        assert(result.equals(Collections.singletonList(session)));
    }

    @Test
    public void toDto_shouldReturnNull_ifSessionNull() {
        Session nullSession = null;
        SessionDto result = mapper.toDto(nullSession);

        assertNull(result);
    }

    @Test
    public void shouldMapEntityToDto() {
        SessionDto result = mapper.toDto(session);

        assert(result.equals(sessionDto));
        assert(result.getId().equals(sessionDto.getId()));
        assert(result.getName().equals(sessionDto.getName()));
        assert(result.getDate().equals(sessionDto.getDate()));
        assert(result.getTeacher_id().equals(sessionDto.getTeacher_id()));
        assert(result.getDescription().equals(sessionDto.getDescription()));
        assert(result.getUsers().equals(sessionDto.getUsers()));
        assert(result.getCreatedAt().equals(sessionDto.getCreatedAt()));
        assert(result.getUpdatedAt().equals(sessionDto.getUpdatedAt()));
    }

    @Test
    public void toListDto_shouldReturnNull_ifSessionListNull() {
        List<Session> nullSessionList = null;
        List<SessionDto> result = mapper.toDto(nullSessionList);

        assertNull(result);
    }

    @Test
    public void shouldMapListEntityToListDto() {
        List<SessionDto> result = mapper.toDto(Collections.singletonList(session));

        assert(result.equals(Collections.singletonList(sessionDto)));
    }
}