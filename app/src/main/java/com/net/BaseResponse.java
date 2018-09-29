package com.net;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class BaseResponse {

    /**
     * code : 200
     * result : {"Id":"3","Tel":"13100629098","PassWord":"","RealName":"","Company":"","Img":"","IdCard":"","Sex":"","StartDate":"","EndDate":"","CertificateId":"","Transport":"","Address":""}
     */

    private int code;
    private String message;


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
}
