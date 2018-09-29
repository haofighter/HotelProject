package com.net.md5;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class BaseResponse1 {

    /**
     * code : 200
     * result : {"Id":"3","Tel":"13100629098","PassWord":"","RealName":"","Company":"","Img":"","IdCard":"","Sex":"","StartDate":"","EndDate":"","CertificateId":"","Transport":"","Address":""}
     */

    private int code;
    private String message;
    /**
     * result : u3ns7pxwEeijgwAFKcIb1g
     */

    private String result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
