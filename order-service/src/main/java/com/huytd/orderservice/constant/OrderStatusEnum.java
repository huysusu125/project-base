package com.huytd.orderservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
public enum OrderStatusEnum {
    PENDING(0), // đang chờ xử lý
    PROCESSING(1), // đang xử lý
    SHIPPED(2), // đa giao cho đơn vị vẫn chuyển
    DELIVERED(3), // đã được giao hàng thành công
    CANCELLED(4), // đã huỷ
    REFUNDED(5), // đã hoàn tiền
    COMPLETED(6), // đã hoàn tất

    ;

    @Getter
    private final Integer code;

    public static OrderStatusEnum get(Integer code) {
        return Objects.requireNonNull(Arrays.stream(OrderStatusEnum.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElse(null));
    }
}
