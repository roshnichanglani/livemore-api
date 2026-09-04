package com.roshni.livemoreapi.service;

import com.roshni.livemoreapi.entity.User;
import com.roshni.livemoreapi.exception.ResourceNotFoundException;
import com.roshni.livemoreapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailAndIsDeletedFalse(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).filter(user -> !user.isDeleted());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !u.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if(updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }

        if(updatedUser.getAvatarUrl() != null) {
            existingUser.setAvatarUrl(updatedUser.getAvatarUrl());
        }

        if(updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }

        return userRepository.save(existingUser);
    }
}
