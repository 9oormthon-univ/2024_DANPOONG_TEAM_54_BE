package com.example.paperplane.domain.user.service;

import com.example.paperplane.domain.user.dto.KakaoUserRequest;
import com.example.paperplane.domain.user.dto.UserIdResponse;
import com.example.paperplane.domain.user.dto.UserProfileResponse;
import com.example.paperplane.domain.user.entity.User;
import com.example.paperplane.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public void updateUsername(Long userId, String newUsername) {
        if (userRepository.existsByUsername(newUsername)) {
            throw new IllegalArgumentException("Username already exists: " + newUsername);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: ID = " + userId));
        user.setUsername(newUsername);
    }

    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: ID = " + userId));
        return new UserProfileResponse(user.getUsername(), user.getProfileImage(), user.getPoints(), user.getUserId());
    }

    public UserIdResponse registerOrGetUser(KakaoUserRequest request) {
        return userRepository.findByKakaoId(request.kakaoId())
                .map(user -> {
                    if (user.isFirstLogin()) {
                        user.setFirstLogin(false);
                    }
                    return new UserIdResponse(user.getUserId(), user.isFirstLogin());
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setKakaoId(request.kakaoId());
                    newUser.setProfileImage(request.profileImage());
                    newUser.setUsername("User_" + request.kakaoId());
                    newUser.setFirstLogin(true);
                    userRepository.save(newUser);
                    return new UserIdResponse(newUser.getUserId(), true);
                });
    }

}
