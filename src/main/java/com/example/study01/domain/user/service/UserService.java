package com.example.study01.domain.user.service;

import com.example.study01.domain.user.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO getById(Long id);
    
    UserDTO getByUsername(String username);
    
    List<UserDTO> getList(String username, Integer status, Boolean isAdmin);
    
    void create(UserDTO userDTO);
    
    void update(UserDTO userDTO);
    
    void delete(Long id);
    
    void updateStatus(Long id, Integer status);
    
    UserDTO login(String username, String password);
    
    boolean checkPermission(Long userId, String permission);
} 