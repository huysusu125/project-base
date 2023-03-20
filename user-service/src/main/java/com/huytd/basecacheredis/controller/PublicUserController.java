package com.huytd.basecacheredis.controller;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.LoginRequest;
import com.huytd.basecacheredis.dto.Oauth2AccessToken;
import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.dto.UserProfileResponse;
import com.huytd.basecacheredis.service.UserService;
import com.huytd.basecacheredis.utils.JwtTokenUtils;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public-api/users")
@RequiredArgsConstructor
public class PublicUserController {

    private final UserService userService;

    @PostMapping()
    public BaseResponse<Long> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PostMapping("/oauth/token")
    public BaseResponse<Oauth2AccessToken> loginByEmail(@RequestBody LoginRequest loginRequest) {
        return userService.loginByEmail(loginRequest);
    }
}
