package com.platform.commons.utils;

/**
 * Created by Administrator on 2017/5/12.
 * Description: 客户端接口JSON数据格式
 */

public class ResponseWrapper<T> {

    private boolean success;
    private int code;
    private String message;
    private T data;

    protected ResponseWrapper() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseWrapper succeed(T body) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(true);
        responseWrapper.setCode(0);
        responseWrapper.setMessage("succeed");
        responseWrapper.setData(body);
        return responseWrapper;
    }

    @SuppressWarnings("unchecked")
    public static ResponseWrapper failed(int code, String message) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setSuccess(false);
        responseWrapper.setCode(code);
        responseWrapper.setMessage(message);
        responseWrapper.setData(null);
        return responseWrapper;
    }
}
