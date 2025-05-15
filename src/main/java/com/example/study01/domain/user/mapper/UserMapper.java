package com.example.study01.domain.user.mapper;

import com.example.study01.domain.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User selectById(Long id);
    
    User selectByUsername(String username);
    
    List<User> selectList(@Param("username") String username, 
                         @Param("status") Integer status,
                         @Param("isAdmin") Boolean isAdmin);
    
    void insert(User user);
    
    void update(User user);
    
    void deleteById(Long id);
    
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
} 