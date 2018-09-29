package com.sun.hotelproject.entity;

import java.io.Serializable;

/**
 *=================================================
 *Author：  hao
 *Time：    2018/7/26 0026
 *Describe: 公安序号申请
 *=================================================
 **/
public class PoliceSerialNumber implements Serializable{
    /**
     * code : 200
     * message :
     * result : McBVQpCiEeiD2gAFKcIb1g
     */

    private int code;
    private String message;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PoliceSerialNumber{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
