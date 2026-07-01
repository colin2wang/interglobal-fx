package com.globalfx.common.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;
    private String message;
    private T data;
    @Builder.Default
    private long timestamp = System.currentTimeMillis();
    @Builder.Default
    private String requestId = UUID.randomUUID().toString();

    public static <T> Result<T> success() {
        return Result.<T>builder().code(200).message("success").build();
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>builder().code(200).message("success").data(data).build();
    }

    public static <T> Result<T> success(String message, T data) {
        return Result.<T>builder().code(200).message(message).data(data).build();
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        return Result.<T>builder().code(resultCode.getCode()).message(resultCode.getMessage()).build();
    }

    public static <T> Result<T> fail(String message) {
        return Result.<T>builder().code(50001).message(message).build();
    }

    public static <T> Result<T> fail(int code, String message) {
        return Result.<T>builder().code(code).message(message).build();
    }
}
