package com.huytd.basecacheredis.mapper.Impl;

import com.huytd.basecacheredis.dto.UserProfileResponse;
import com.huytd.basecacheredis.entity.User;
import com.huytd.basecacheredis.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component("userMapper")
public class UserMapperImpl implements Mapper<UserProfileResponse, User> {

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
}
