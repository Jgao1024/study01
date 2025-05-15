package com.example.study01.interfaces.user;

import com.example.study01.common.result.Result;
import com.example.study01.domain.user.dto.UserDTO;
import com.example.study01.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<UserDTO> login(@Parameter(description = "用户名") @RequestParam String username,
                                @Parameter(description = "密码") @RequestParam String password) {
        return Result.success(userService.login(username, password));
    }

    @Operation(summary = "检查权限")
    @GetMapping("/check-permission")
    public Result<Boolean> checkPermission(@Parameter(description = "用户ID") @RequestParam Long userId,
                                         @Parameter(description = "权限标识") @RequestParam String permission) {
        return Result.success(userService.checkPermission(userId, permission));
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/{id}")
    public Result<UserDTO> getUser(@Parameter(description = "用户ID") @PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public Result<List<UserDTO>> getUserList(@Parameter(description = "用户名") @RequestParam(required = false) String username,
                                           @Parameter(description = "状态") @RequestParam(required = false) Integer status,
                                           @Parameter(description = "是否管理员") @RequestParam(required = false) Boolean isAdmin) {
        return Result.success(userService.getList(username, status, isAdmin));
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
    public Result<Void> deleteUser(@Parameter(description = "用户ID") @PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }

    @Operation(summary = "更新用户状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateUserStatus(@Parameter(description = "用户ID") @PathVariable Long id,
                                       @Parameter(description = "状态") @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success();
    }
} 