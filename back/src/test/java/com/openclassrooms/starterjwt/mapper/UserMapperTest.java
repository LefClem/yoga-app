package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    UserDto userDto;
    User user;

    @BeforeEach
    public void init() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        userDto = new UserDto(1L, "test@gmail.com", "User", "User", true, "password", currentLocalDateTime, currentLocalDateTime);
        user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .firstName("User")
                .lastName("User")
                .admin(true)
                .password("password")
                .createdAt(currentLocalDateTime)
                .updatedAt(currentLocalDateTime)
                .build();
    }

    @Test
    public void toEntity_shouldReturnNull_ifUserDtoNull() {
        UserDto nullUserDto = null;
        User result = mapper.toEntity(nullUserDto);

        assertNull(result);
    }

    @Test
    public void shouldMapDtoToEntity() {
        User result = mapper.toEntity(userDto);

        assert(result.equals(user));
        assert(result.getId().equals(user.getId()));
        assert(result.getEmail().equals(user.getEmail()));
        assert(result.getFirstName().equals(user.getFirstName()));
        assert(result.getLastName().equals(user.getLastName()));
        assert(result.getPassword().equals(user.getPassword()));
        assert(result.isAdmin() == user.isAdmin());
        assert(result.getCreatedAt().equals(user.getCreatedAt()));
        assert(result.getUpdatedAt().equals(user.getUpdatedAt()));
    }

    @Test
    public void toListEntity_shouldReturnNull_ifUserDtoListNull() {
        List<UserDto> nullUserDtoList = null;
        List<User> result = mapper.toEntity(nullUserDtoList);

        assertNull(result);
    }

    @Test
    public void shouldMapListDtoToListEntity() {
        List<User> result = mapper.toEntity(Collections.singletonList(userDto));

        assert(result.equals(Collections.singletonList(user)));
    }

    @Test
    public void toDto_shouldReturnNull_ifUserNull() {
        User nullUser = null;
        UserDto result = mapper.toDto(nullUser);

        assertNull(result);
    }

    @Test
    public void shouldMapEntityToDto() {
        UserDto result = mapper.toDto(user);

        assert(result.equals(userDto));
        assert(result.getId().equals(userDto.getId()));
        assert(result.getEmail().equals(userDto.getEmail()));
        assert(result.getFirstName().equals(userDto.getFirstName()));
        assert(result.getLastName().equals(userDto.getLastName()));
        assert(result.getPassword().equals(userDto.getPassword()));
        assert(result.isAdmin() == userDto.isAdmin());
        assert(result.getCreatedAt().equals(userDto.getCreatedAt()));
        assert(result.getUpdatedAt().equals(userDto.getUpdatedAt()));
    }

    @Test
    public void toListDto_shouldReturnNull_ifUserListNull() {
        List<User> nullUserList = null;
        List<UserDto> result = mapper.toDto(nullUserList);

        assertNull(result);
    }

    @Test
    public void shouldMapListEntityToListDto() {
        List<UserDto> result = mapper.toDto(Collections.singletonList(user));

        assert(result.equals(Collections.singletonList(userDto)));
    }
}