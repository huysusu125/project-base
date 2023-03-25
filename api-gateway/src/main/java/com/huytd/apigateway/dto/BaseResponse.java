package com.huytd.apigateway.dto;

import lombok.Data;

@Data
public class BaseResponse {
    private String message = "successfully";
    private Integer code = 200;
    private String data;
}
