package org.seydaliev.service;

import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.seydaliev.exceptions.ResourceNotFoundException;
import org.seydaliev.model.Users;
import org.seydaliev.repository.UsersRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UsersServiceTest {
    @Mock
    private UsersRepository userRepository;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllUsers() {
        Users user1 = new Users(1L, "User1", "user1@example.com");
        Users user2 = new Users(2L, "User2", "user2@example.com");
        List<Users> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<Users> foundUsers = usersService.findAll();

        assertEquals(users.size(), foundUsers.size());
        assertEquals(user1.getName(), foundUsers.get(0).getName());
        assertEquals(user2.getName(), foundUsers.get(1).getName());
    }

    @Test
    public void testFindUserById() {
        Users user = new Users(1L, "User1", "user1@example.com");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Users foundUser = usersService.findById(1L);

        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    public void testCreateUser() {
        Users user = new Users();
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(userRepository.save(any(Users.class))).thenReturn(user);

        Users createdUser = usersService.createUser(user);

        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testFindUserByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usersService.findById(1L));
    }

    @Test
    public void testDeleteUser() {
        Long id = 1L;
        doNothing().when(userRepository).deleteById(any(Long.class));
        usersService.deleteUser(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateUser() {
        Long id = 1L;
        Users updateUser = new Users();
        updateUser.setName("Nikolay");
        updateUser.setEmail("nikolay@example.ru");

        Users existingUser = new Users();
        existingUser.setId(id);
        existingUser.setName("Alex");
        existingUser.setEmail("alex@example.ru");

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(Users.class))).thenReturn(updateUser);

        Users users = usersService.updateUser(id, updateUser);

        assertEquals(updateUser.getName(), users.getName());
        assertEquals(updateUser.getEmail(), users.getEmail());
    }

}
