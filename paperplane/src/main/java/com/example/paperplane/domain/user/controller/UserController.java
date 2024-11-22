package com.example.paperplane.domain.user.controller;

import com.example.paperplane.domain.user.dto.KakaoUserRequest;
import com.example.paperplane.domain.user.dto.UserIdResponse;
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

    @PostMapping("/kakao")
    @Operation(summary = "Kakao 사용자 등록 또는 기존 사용자 가져오기", description = "첫 로그인 → isFirstLogin 값 =  true\n" +
            "\n" +
            "첫 로그인 이후 → isFirstLogin 값 =  false")
    public ResponseEntity<UserIdResponse> registerOrGetUser(@RequestBody KakaoUserRequest request) {
        UserIdResponse response = userService.registerOrGetUser(request);
        return ResponseEntity.ok(response);
    }

}

