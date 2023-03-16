package com.huytd.basecacheredis.controller;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public BaseResponse<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @GetMapping()
    public BaseResponse<?> getUserInfo( ) {
        return userService.getUserInfo();
    }
}
