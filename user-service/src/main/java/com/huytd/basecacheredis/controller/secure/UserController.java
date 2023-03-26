package com.huytd.basecacheredis.controller.secure;

import com.huytd.basecacheredis.dto.BaseResponse;
import com.huytd.basecacheredis.dto.UserProfileResponse;
import com.huytd.basecacheredis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public BaseResponse<UserProfileResponse> getUserInfo( ) {
        return userService.getUserInfo();
    }
}
