package com.example.study01.domain.user.service.impl;

import com.example.study01.common.exception.BusinessException;
import com.example.study01.domain.user.dto.UserDTO;
import com.example.study01.domain.user.entity.User;
import com.example.study01.domain.user.mapper.UserMapper;
import com.example.study01.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO getById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return convertToDTO(user);
    }

    @Override
    public UserDTO getByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null ? convertToDTO(user) : null;
    }

    @Override
    public List<UserDTO> getList(String username, Integer status, Boolean isAdmin) {
        List<User> users = userMapper.selectList(username, status, isAdmin);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserDTO userDTO) {
        // 检查用户名是否已存在
        if (getByUsername(userDTO.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        // 加密密码
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // 设置默认值
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : 1);
        user.setIsAdmin(userDTO.getIsAdmin() != null ? userDTO.getIsAdmin() : false);
        
        userMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        
        // 如果修改密码，需要加密
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        
        userMapper.update(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        userMapper.updateStatus(id, status);
    }

    @Override
    public UserDTO login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        
        return convertToDTO(user);
    }

    @Override
    public boolean checkPermission(Long userId, String permission) {
        // 这里简单实现，只判断是否是管理员
        User user = userMapper.selectById(userId);
        return user != null && user.getIsAdmin();
    }

    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        // 不返回密码
        userDTO.setPassword(null);
        return userDTO;
    }
} 