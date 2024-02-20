package org.seydaliev.service;

import org.seydaliev.model.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> findAll();
    Users findById(Long id);
    Users createUser(Users user);
    void deleteUser(Long id);
    Users updateUser(Long id, Users updateUser);
}
