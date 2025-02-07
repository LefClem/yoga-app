package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SessionServicesTest {
    @MockBean
    SessionRepository sessionRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    SessionService sessionService;

    final Teacher mockTeacher = Teacher.builder()
            .firstName("Albus")
            .lastName("Dumbeldore")
            .build();

    final User mockUser = User.builder()
            .id(1L)
            .email("johndoe@mail.com")
            .firstName("John")
            .lastName("Doe")
            .password("test!1234")
            .admin(false)
            .build();

    final Session mockSession = Session.builder()
            .id(1L)
            .name("Session 1")
            .date(new Date())
            .description("Experienced session")
            .teacher(mockTeacher)
            .users(new ArrayList<>())
            .build();

    @Test
    @DisplayName("Should create a new Session")
    void test_CreateSession(){
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        Session session = sessionService.create(mockSession);

        assertThat(session).isEqualTo(mockSession);
        verify(sessionRepository, times(1)).save(mockSession);
    }

    @Test
    @DisplayName("Should delete a session by his id")
    void test_DeleteSession(){
        sessionService.delete(1L);

        verify(sessionRepository).deleteById(1L);
        verify(sessionRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return the list of sessions")
    void test_FindAllSessions(){
        sessionService.findAll();

        verify(sessionRepository).findAll();
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return a session by his id")
    void test_FindSessionById(){
        sessionService.getById(1L);

        verify(sessionRepository).findById(1L);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should update a session")
    void test_UpdateSession(){
        sessionService.update(1L, mockSession);

        verify(sessionRepository).save(mockSession);
    }

    @Test
    @DisplayName("Should participate to the session")
    void test_Participate(){
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        sessionService.participate(1L, 1L);

        verify(sessionRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should not participate to the session")
    void test_Unparticipate(){
        List<User> users = new ArrayList<>();
        users.add(mockUser);
        mockSession.setUsers(users);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        sessionService.noLongerParticipate(1L, 1L);

        verify(sessionRepository).save(mockSession);
    }
}
