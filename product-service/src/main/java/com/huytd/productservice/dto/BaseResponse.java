package com.huytd.productservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    @Builder.Default
    private String message = "successfully";
    @Builder.Default
    private Integer code = 200;
    private T data;

    public static <T> BaseResponse<T> successResponse(T data) {
        return BaseResponse.<T>builder()
                .message("successfully")
                .code(200)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> errorResponse(String message, int code) {
        return BaseResponse.<T>builder()
                .message(message)
                .code(code)
                .build();
    }
}
