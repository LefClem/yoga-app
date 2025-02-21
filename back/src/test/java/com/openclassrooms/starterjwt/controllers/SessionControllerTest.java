package com.openclassrooms.starterjwt.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.SessionService;

@SpringBootTest
@ActiveProfiles("test")
class SessionControllerTest {
	
	@Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    private Session mockSession;

    @BeforeEach
    public void setup() {
    	List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User(1L, "user1@mail.com", "User", "USER", "password", false, LocalDateTime.now(), LocalDateTime.now()));
        mockUsers.add(new User(2L, "user2@mail.com", "Test", "TEST", "password", true, LocalDateTime.now(), LocalDateTime.now()));
    	
        Teacher mockTeacher = new Teacher(1L, "McGonagall", "Minerva", LocalDateTime.now(), LocalDateTime.now());
        
    	this.mockSession = new Session(1L, "Session 1", new Date(), "description 1", mockTeacher, mockUsers, LocalDateTime.now(), LocalDateTime.now());
    }
    
    @Test
    void findByIdSuccessTest() {
        
        String sessionId = "1";
        Session session = this.mockSession;
        when(sessionService.getById(anyLong())).thenReturn(session);

        ResponseEntity<?> response = sessionController.findById(sessionId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(this.sessionMapper.toDto(session));
    }
    
    @Test
    void findByIdNotFoundTest() {
        String sessionId = "1";
        when(sessionService.getById(anyLong())).thenReturn(null);

        ResponseEntity<?> response = sessionController.findById(sessionId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findByIdBadRequestTest() {
        String invalidSessionId = "invalidId";

        ResponseEntity<?> response = sessionController.findById(invalidSessionId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findAllSuccessTest() {
        List<Session> sessions = List.of(this.mockSession);
        when(sessionService.findAll()).thenReturn(sessions);

        ResponseEntity<?> response = sessionController.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(this.sessionMapper.toDto(sessions));
    }
    
    @Test
    void createSuccessTest() {
        SessionDto sessionDto = new SessionDto();
        Session session = this.mockSession;
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.create(sessionDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(this.sessionMapper.toDto(session));
    }

    @Test
    void updateSuccessTest() {
        String sessionId = "1";
        SessionDto sessionDto = new SessionDto();
        Session session = this.mockSession;
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.update(anyLong(), eq(session))).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.update(sessionId, sessionDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(this.sessionMapper.toDto(session));
    }
    
    @Test
    void updateBadRequestTest() {
        String invalidSessionId = "invalidId";
        SessionDto sessionDto = new SessionDto();

        ResponseEntity<?> response = sessionController.update(invalidSessionId, sessionDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteSuccessTest() {
        String sessionId = "1";
        Session session = this.mockSession;
        when(sessionService.getById(anyLong())).thenReturn(session);

        ResponseEntity<?> response = sessionController.save(sessionId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(sessionService, times(1)).delete(Long.parseLong(sessionId));
    }
    
    @Test
    void deleteNotFoundTest() {
        String invalidSessionId = "1";

        ResponseEntity<?> response = sessionController.save(invalidSessionId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
    @Test
    void deleteBadRequestTest() {
        String invalidSessionId = "invalidId";

        ResponseEntity<?> response = sessionController.save(invalidSessionId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void participateSuccessTest() {
        String sessionId = "1";
        String userId = "1";

        ResponseEntity<?> response = sessionController.participate(sessionId, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(sessionService, times(1)).participate(Long.parseLong(sessionId), Long.parseLong(userId));
    }
    
    @Test
    void participateBadRequestTest() {
        String invalidSessionId = "invalidId";
        String userId = "1";

        ResponseEntity<?> response = sessionController.participate(invalidSessionId, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void noLongerParticipateSuccessTest() {
        String sessionId = "1";
        String userId = "1";

        ResponseEntity<?> response = sessionController.noLongerParticipate(sessionId, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(sessionService, times(1)).noLongerParticipate(Long.parseLong(sessionId), Long.parseLong(userId));
    }
    
    @Test
    void noLongerParticipateBadRequestTest() {
        String invalidSessionId = "invalidId";
        String userId = "1";

        ResponseEntity<?> response = sessionController.noLongerParticipate(invalidSessionId, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}