package com.huytd.basecacheredis.dto;

import lombok.Data;

@Data
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String roleType;
}
