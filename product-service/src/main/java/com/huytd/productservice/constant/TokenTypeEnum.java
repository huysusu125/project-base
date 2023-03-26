package com.huytd.productservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TokenTypeEnum {
    ACCESS_TOKEN(0),
    REFRESH_TOKEN(1),


    ;


    @Getter
    private Integer code;
}
