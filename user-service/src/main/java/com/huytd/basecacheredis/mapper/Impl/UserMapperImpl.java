package com.huytd.basecacheredis.mapper.Impl;

import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.dto.UserProfileResponse;
import com.huytd.basecacheredis.entity.User;
import com.huytd.basecacheredis.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("userMapper")
@RequiredArgsConstructor
public class UserMapperImpl implements Mapper<UserProfileResponse, User, RegisterRequest> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResponse toDto(User entity) {
        return UserProfileResponse
                .builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .roleType(entity.getRoleType())
                .build();
    }

    @Override
    public User toEntity(RegisterRequest request) {
        return User
                .builder()
                .email(request.getEmail())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }


}
