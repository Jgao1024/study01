package com.example.study01.domain.user.service;

import com.example.study01.domain.user.dto.UserDTO;

public interface UserService {
    UserDTO getById(Long id);
    void create(UserDTO userDTO);
    void update(UserDTO userDTO);
    void delete(Long id);
} 