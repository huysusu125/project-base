package com.huytd.basecacheredis.controller;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.LoginRequest;
import com.huytd.basecacheredis.dto.Oauth2AccessToken;
import com.huytd.basecacheredis.dto.RefreshTokenRequest;
import com.huytd.basecacheredis.service.UserService;
import com.huytd.basecacheredis.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class Oauth2AccessTokenController {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @PostMapping("/token")
    public BaseResponse<Oauth2AccessToken> loginByEmail(@RequestBody LoginRequest loginRequest) {
        return userService.loginByEmail(loginRequest);
    }

    @GetMapping("/token_key")
    public BaseResponse<String> getTokenKey() {
        BaseResponse<String> response = new BaseResponse<>();
        response.setData(jwtTokenUtils.getPublicKey());
        return response;
    }

    @PostMapping("/refresh_token")
    public BaseResponse<Oauth2AccessToken> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);
    }
}
