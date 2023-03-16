package com.huytd.basecacheredis.service.impl;

import com.huytd.basecacheredis.constant.ErrorCodes;
import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.entity.User;
import com.huytd.basecacheredis.exception.UserRegistrationException;
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

    @Override
    public BaseResponse<?> register(RegisterRequest registerRequest) {
        User user = userRepository.findByUsername(registerRequest.getEmail());
        if (user != null) {
            throw new UserRegistrationException(Collections.singletonList(ErrorCodes.USER_ALREADY_EXISTED));
        }
        user = userRepository.save(User
                .builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build());
        return BaseResponse.builder().data(user.getId()).build();
    }

    @Override
    public BaseResponse<?> getUserInfo() {
        BaseResponse baseResponse = new BaseResponse();
        Long userId = ServletUtils.getCurrentUserId();
        if (userId == null) {
            return baseResponse;
        }
        User user = userCacheUtils.getUserFromCache(userId);
        if (user == null) {
            user = userRepository.findById(userId).orElse(null);
        }
        baseResponse.setData(user);
        return baseResponse;
    }
}
