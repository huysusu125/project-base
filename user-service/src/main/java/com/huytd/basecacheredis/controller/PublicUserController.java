package com.huytd.basecacheredis.controller;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.RegisterRequest;
import com.huytd.basecacheredis.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

}
