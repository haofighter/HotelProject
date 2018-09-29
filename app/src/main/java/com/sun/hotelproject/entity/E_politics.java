package com.sun.hotelproject.entity;

import java.util.List;

/**
 * Created by a'su's on 2018/5/30.
 */

public class E_politics {
    private String result;//返回结果
    private String rescode;//状态码
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
        private String appid;//商户号
        private String mac_auth_id;//商家授权凭证
        private String auth_id;//腾讯授权单号
        private String openid;//用户openid
        private String bteid;//用户bteid
        private String real_tag;//用户实名标记 1 已实名 0 未实名
        private String encryptname;//姓名
        private String encryptidno;//证件号
        private String ps_de_skey;//三所返回的数字信封 人脸通过返回
        private String ps_cipher_data;//三所返回的用户实名密文  人脸通过返回
        private String real_face; //人脸识别通过与否 1 通过 2 不通过
        private String result_code;//业务结果
        private String err_code;//错误代码  人脸通过返回
        private String err_code_des;//错误代码描述 人脸通过返回
        private String sign_type;//签名类型
        private String optiontime;//操作时间
        private String imgsite;//人脸图片存放地址
        private String qrcodeid;//二维码正文的id
        private String eid;//二维码所代表证件类型
        private String createtime;//二维码生成时间
        private String validtime;//有效时间
        private String qrcode;//二维码
        private String idtype;//证件类型 人脸通过返回
//        private String sign;//签名
//        private String encrytname;//用户姓名
//        private String encrytidno;//证件号


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

        public String getMac_auth_id() {
            return mac_auth_id;
        }

        public void setMac_auth_id(String mac_auth_id) {
            this.mac_auth_id = mac_auth_id;
        }

        public String getAuth_id() {
            return auth_id;
        }

        public void setAuth_id(String auth_id) {
            this.auth_id = auth_id;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getBteid() {
            return bteid;
        }

        public void setBteid(String bteid) {
            this.bteid = bteid;
        }

        public String getReal_tag() {
            return real_tag;
        }

        public void setReal_tag(String real_tag) {
            this.real_tag = real_tag;
        }

        public String getEncryptname() {
            return encryptname;
        }

        public void setEncryptname(String encryptname) {
            this.encryptname = encryptname;
        }

        public String getEncryptidno() {
            return encryptidno;
        }

        public void setEncryptidno(String encryptidno) {
            this.encryptidno = encryptidno;
        }

        public String getPs_de_skey() {
            return ps_de_skey;
        }

        public void setPs_de_skey(String ps_de_skey) {
            this.ps_de_skey = ps_de_skey;
        }

        public String getPs_cipher_data() {
            return ps_cipher_data;
        }

        public void setPs_cipher_data(String ps_cipher_data) {
            this.ps_cipher_data = ps_cipher_data;
        }

        public String getReal_face() {
            return real_face;
        }

        public void setReal_face(String real_face) {
            this.real_face = real_face;
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

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public String getOptiontime() {
            return optiontime;
        }

        public void setOptiontime(String optiontime) {
            this.optiontime = optiontime;
        }

        public String getImgsite() {
            return imgsite;
        }

        public void setImgsite(String imgsite) {
            this.imgsite = imgsite;
        }

        public String getQrcodeid() {
            return qrcodeid;
        }

        public void setQrcodeid(String qrcodeid) {
            this.qrcodeid = qrcodeid;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getValidtime() {
            return validtime;
        }

        public void setValidtime(String validtime) {
            this.validtime = validtime;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getIdtype() {
            return idtype;
        }

        public void setIdtype(String idtype) {
            this.idtype = idtype;
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "version='" + version + '\'' +
                    ", appid='" + appid + '\'' +
                    ", mac_auth_id='" + mac_auth_id + '\'' +
                    ", auth_id='" + auth_id + '\'' +
                    ", openid='" + openid + '\'' +
                    ", bteid='" + bteid + '\'' +
                    ", real_tag='" + real_tag + '\'' +
                    ", encryptname='" + encryptname + '\'' +
                    ", encryptidno='" + encryptidno + '\'' +
                    ", ps_de_skey='" + ps_de_skey + '\'' +
                    ", ps_cipher_data='" + ps_cipher_data + '\'' +
                    ", real_face='" + real_face + '\'' +
                    ", result_code='" + result_code + '\'' +
                    ", err_code='" + err_code + '\'' +
                    ", err_code_des='" + err_code_des + '\'' +
                    ", sign_type='" + sign_type + '\'' +
                    ", optiontime='" + optiontime + '\'' +
                    ", imgsite='" + imgsite + '\'' +
                    ", qrcodeid='" + qrcodeid + '\'' +
                    ", eid='" + eid + '\'' +
                    ", createtime='" + createtime + '\'' +
                    ", validtime='" + validtime + '\'' +
                    ", qrcode='" + qrcode + '\'' +
                    ", idtype='" + idtype + '\'' +
                    '}';
        }
    }

}
