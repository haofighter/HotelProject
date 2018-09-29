package com.sun.hotelproject.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by a'su's on 2018/7/16.
 */

public class MacKey implements Serializable {
    private String result;//返回结果
    private String rescode;//返回值
    private List<Bean> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public List<Bean> getList() {
        return list;
    }

    public void setList(List<Bean> list) {
        this.list = list;
    }

    public class Bean{
        private String version;//协议版本号
        private String appid;//商家接入id;
        private String key_num;//根秘钥个数
        private String mac_key_list;//根密钥列表
        private String result_code;//业务结果
        private String err_code;//错误代码
        private String err_code_des;//错误代码描述
        private String optiontime;//操作时间
        private String flag;//标记植

        @Override
        public String toString() {
            return "Bean{" +
                    "version='" + version + '\'' +
                    ", appid='" + appid + '\'' +
                    ", key_num='" + key_num + '\'' +
                    ", mac_key_list='" + mac_key_list + '\'' +
                    ", result_code='" + result_code + '\'' +
                    ", err_code='" + err_code + '\'' +
                    ", err_code_des='" + err_code_des + '\'' +
                    ", optiontime='" + optiontime + '\'' +
                    ", flag='" + flag + '\'' +
                    '}';
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getKey_num() {
            return key_num;
        }

        public void setKey_num(String key_num) {
            this.key_num = key_num;
        }

        public String getMac_key_list() {
            return mac_key_list;
        }

        public void setMac_key_list(String mac_key_list) {
            this.mac_key_list = mac_key_list;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getErr_code() {
            return err_code;
        }

        public void setErr_code(String err_code) {
            this.err_code = err_code;
        }

        public String getErr_code_des() {
            return err_code_des;
        }

        public void setErr_code_des(String err_code_des) {
            this.err_code_des = err_code_des;
        }

        public String getOptiontime() {
            return optiontime;
        }

        public void setOptiontime(String optiontime) {
            this.optiontime = optiontime;
        }

    }

}
