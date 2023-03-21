package com.huytd.basecacheredis.service;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.LoginRequest;
import com.huytd.basecacheredis.dto.Oauth2AccessToken;
import com.huytd.basecacheredis.dto.RefreshTokenRequest;
import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.dto.UserProfileResponse;


public interface UserService {
    BaseResponse<Long> register(RegisterRequest registerRequest);

    BaseResponse<UserProfileResponse> getUserInfo();

    BaseResponse<Oauth2AccessToken> loginByEmail(LoginRequest loginRequest);

    BaseResponse<Oauth2AccessToken> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
