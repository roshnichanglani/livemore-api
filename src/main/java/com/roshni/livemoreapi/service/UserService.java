package com.roshni.livemoreapi.service;

import com.roshni.livemoreapi.entity.User;

import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    void deleteUser(Long id);

    User updateUser(Long id, User updatedUser);
}
