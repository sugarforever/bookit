package com.wiysoft.mvc.m;

/**
 * Created by weiliyang on 1/12/16.
 */
public class RestfulResponse {

    private int code;
    private String message;
    private Object data;

    public RestfulResponse(final int code, final String message) {
        this(code, message, null);
    }

    public RestfulResponse(final int code, final String message, final Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
