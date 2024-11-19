package com.example.paperplane.domain.user.controller;

import com.example.paperplane.domain.user.dto.UserProfileResponse;
import com.example.paperplane.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "user", description = "User 관련 API")
public class UserController {
    private final UserService userService;

    @PatchMapping("/{id}/username")
    @Operation(summary = "닉네임 변경")
    public ResponseEntity<String> updateUsername(
            @PathVariable Long id,
            @RequestParam String newUsername
    ) {
        userService.updateUsername(id, newUsername);
        return ResponseEntity.ok("Username updated successfully");
    }

    @GetMapping("/{id}/profile")
    @Operation(summary = "프로필 확인")
    public UserProfileResponse getUserProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }
}

