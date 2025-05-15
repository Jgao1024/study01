package com.example.study01.domain.user.mapper;

import com.example.study01.domain.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectById(Long id);
    void insert(User user);
    void update(User user);
    void deleteById(Long id);
} 