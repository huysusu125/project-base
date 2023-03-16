package com.huytd.basecacheredis.service;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.RegisterRequest;


public interface UserService {
    BaseResponse<?> register(RegisterRequest registerRequest);

    BaseResponse<?> getUserInfo();
}
