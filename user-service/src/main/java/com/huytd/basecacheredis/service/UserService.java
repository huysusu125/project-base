package com.huytd.basecacheredis.service;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.dto.UserProfileResponse;


public interface UserService {
    BaseResponse<?> register(RegisterRequest registerRequest);

    BaseResponse<UserProfileResponse> getUserInfo();
}
