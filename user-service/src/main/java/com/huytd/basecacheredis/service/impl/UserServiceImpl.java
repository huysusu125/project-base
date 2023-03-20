package com.huytd.basecacheredis.service.impl;

import com.huytd.basecacheredis.constant.ErrorCodes;
import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.LoginRequest;
import com.huytd.basecacheredis.dto.Oauth2AccessToken;
import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.dto.UserProfileResponse;
import com.huytd.basecacheredis.entity.User;
import com.huytd.basecacheredis.exception.BadRequestException;
import com.huytd.basecacheredis.exception.UserRegistrationException;
import com.huytd.basecacheredis.mapper.Mapper;
import com.huytd.basecacheredis.repository.UserRepository;
import com.huytd.basecacheredis.service.UserService;
import com.huytd.basecacheredis.utils.ServletUtils;
import com.huytd.basecacheredis.utils.UserCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCacheUtils userCacheUtils;
    private final Mapper<UserProfileResponse, User> userMapper;
    @Override
    public BaseResponse<Long> register(RegisterRequest registerRequest) {
        BaseResponse<Long> response = new BaseResponse<>();
        User user = userRepository.findByUsername(registerRequest.getEmail()).orElse(null);
        if (user != null) {
            throw new UserRegistrationException(Collections.singletonList(ErrorCodes.USER_ALREADY_EXISTED));
        }
        user = userRepository.save(User
                .builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build());
        response.setData(user.getId());
        return response;
    }

    @Override
    public BaseResponse<UserProfileResponse> getUserInfo() {
        BaseResponse<UserProfileResponse> baseResponse = new BaseResponse<>();
        Long userId = ServletUtils.getCurrentUserId();
        if (userId == null) {
            return baseResponse;
        }
        User user = userCacheUtils.getUserFromCache(userId);
        if (user == null) {
            user = userRepository
                    .findById(userId).orElse(null);
            if (user != null) {
                userCacheUtils.saveUserToCache(user);
            }

        }
        baseResponse.setData(userMapper.toDto(user));
        return baseResponse;
    }

    @Override
    public BaseResponse<Oauth2AccessToken> loginByEmail(LoginRequest loginRequest) {
        User user = userRepository
                .findByUsername(loginRequest.getEmail())
                .orElseThrow(() -> new BadRequestException(Collections.singletonList(ErrorCodes.USER_NOT_FOUND)));


        return null;
    }
}
