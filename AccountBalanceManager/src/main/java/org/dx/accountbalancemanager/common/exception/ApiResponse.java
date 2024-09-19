package org.dx.accountbalancemanager.common.exception;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private String resultCode;
    private String msg;
    private T data;

    public ApiResponse(String resultCode, String msg, T data) {
        this.resultCode = resultCode;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String resultCode, String msg, T data) {
        return new ApiResponse<>(resultCode, msg, data);
    }
    public static <T> ApiResponse<T> error(String resultCode, String msg, T data) {
        return new ApiResponse<>(resultCode, msg, null);
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}



