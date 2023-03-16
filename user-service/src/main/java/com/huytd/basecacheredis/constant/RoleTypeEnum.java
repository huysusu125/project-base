package com.huytd.basecacheredis.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
public enum RoleTypeEnum {
    END_USER(0);

    @Getter
    private Integer code;

    public static RoleTypeEnum get(Integer code) {
        return Objects.requireNonNull(Arrays.stream(RoleTypeEnum.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElse(null));
    }
}
