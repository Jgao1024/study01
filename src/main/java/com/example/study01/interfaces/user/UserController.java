package com.example.study01.interfaces.user;

import com.example.study01.common.result.Result;
import com.example.study01.domain.user.dto.UserDTO;
import com.example.study01.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "获取用户信息")
    @GetMapping("/{id}")
    public Result<UserDTO> getUser(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    @Operation(summary = "创建用户")
    @PostMapping
    public Result<Void> createUser(@Valid @RequestBody UserDTO userDTO) {
        userService.create(userDTO);
        return Result.success();
    }

    @Operation(summary = "更新用户")
    @PutMapping
    public Result<Void> updateUser(@Valid @RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }
} 