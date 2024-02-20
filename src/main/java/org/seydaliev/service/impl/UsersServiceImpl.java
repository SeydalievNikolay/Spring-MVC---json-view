package org.seydaliev.service.impl;

import org.seydaliev.exceptions.ResourceNotFoundException;
import org.seydaliev.exceptions.UserAlreadyExistsException;
import org.seydaliev.exceptions.UserNotFoundException;
import org.seydaliev.model.Users;
import org.seydaliev.repository.UsersRepository;
import org.seydaliev.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public Users createUser(Users user) {
        if (usersRepository.existsById(user.getId())) {
            throw new UserAlreadyExistsException("User with id" + user.getId() + "already exists");
        }
        return usersRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id" + id);
        }
        usersRepository.deleteById(id);
    }

    @Override
    public Users updateUser(Long id, Users updateUser) {
        return usersRepository.findById(id)
                .map(users -> {
                    users.setEmail(updateUser.getEmail());
                    users.setName(updateUser.getName());
                    return usersRepository.save(users);
                })
                .orElseThrow(()-> new UserNotFoundException("User not found with id " + id));
    }
}
