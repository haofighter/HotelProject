package com.sun.hotelproject.entity;

import java.io.Serializable;

/**
 * TODO 多用实体 时间 2017/6/30
 */
public class Draw implements Serializable {
    private String result;//处理结果
    private String rescode;//响应码
    private String codeimgurl; //返回的二维码地址
    private String orderid;//订单号
    private String smscode;//验证码
    private String docno;//身份证号
    private String sernumber;//验证码
    private String rpmsno;//验证码

    @Override
    public String toString() {
        return "Draw{" +
                "result='" + result + '\'' +
                ", rescode='" + rescode + '\'' +
                ", codeimgurl='" + codeimgurl + '\'' +
                ", orderid='" + orderid + '\'' +
                ", smscode='" + smscode + '\'' +
                ", docno='" + docno + '\'' +
                ", sernumber='" + sernumber + '\'' +
                ", rpmsno='" + rpmsno + '\'' +
                ", list=" + list +
                '}';
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getSernumber() {
        return sernumber;
    }

    public void setSernumber(String sernumber) {
        this.sernumber = sernumber;
    }

    public String getRpmsno() {
        return rpmsno;
    }

    public void setRpmsno(String rpmsno) {
        this.rpmsno = rpmsno;
    }

    /**
     * list : {"rmk":"","rpmsno":"1014","address":"湖北省麻城市宋埠镇彭店村三组枣树垸33号               ","nation":"汉族","name":"张浩             ","birth":"1992-08-04","tel":"","pic":"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABsSFBcUERsXFhceHBsgKEIrKCUlKFE6PTBCYFVlZF9V\nXVtqeJmBanGQc1tdhbWGkJ6jq62rZ4C8ybqmx5moq6T/2wBDARweHigjKE4rK06kbl1upKSkpKSk\npKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKT/wAARCAB+AGYDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDpaKKK\nACiignAyaACjp1qjNq9pFuG/cw7AdaxrzVJro4zsT+6D/OgDoxcQl9nmpuHbNSAgjIII9q4wyGrN\nnqc1ocL8yf3TQB1VFZ1nrEFw2xx5be54NaNABRRRQAUUUUAFFFFAASAMnpWNq+pp5Rht3O49WFN1\nnUPlMKZHv61ixK0rZJzQCI9tNINXjbjZnFQSoFGRSTKaIBu9TTgT3pucGlJpkDunIrb0nVHDJBO2\nQeAx6isHcaUOQaBndUVnaJefabXax+ePg/StGgAooooAKrahN5Fszd8YFWayPEcuy3jUdS36UAYE\nspkJZupq3pqBgaoAZYAVp2CsinipZUSx5RU7TyDVK7hIDADjPFaBlcH/AFYp3yzLtZQKlaGjOfKH\nj3pFUmtiazQKSOcDiqEcD8lRVXIcSuykUyr8ltJs3be1UnUjNNMlou6Nctb3qEfdY7SK66uEiYrI\nCDjBrt7aQS28bjuopiJKKKKACsPxN9yICtyqGtW/n2TMOsfzCgDmLRDJMABW0D5KD5eaztKH+lYP\npW6UBFQzSJmyXcm5U8v73epItzHJGKt+VmlCDcBUlkc0R8v61QkMkIPlpnBrZdcqM1EYgeRQgKET\nzug3RjBH5VDd6eTGXUcithExRJjYfpTuS0cgow+O9dppv/IPgz/cFcm8eb18dA1dhaENbRkDAKji\nrRDViWiiimSFNkQSRsh6EYp1FAHLxRG11BkPGMithDUWrQATR3AHsaVGGAQahmsWWeMVVluRG/K5\n96m3gDmq0jxk8mpLsOe/AwNpb6CrEEm8ZxgVUR4B3qykiEYBoCxM2BVW4YhGPsalZ6qXrgQsM4zx\nTRLKGnwtcO3HLN1rqI1CIqjgAYrJ0RVEkgXkKorXq0ZyfQKKKKZIUUUUAZ+tS+VbBsZOazrCfzIR\nnqvFaupWhuYGCnnjiqr2SWlqu0fN3NJlpjWO4YzUBiUckbvrSh+eakBU9azNEyNUjPHlgVIiKnKm\nnfJTXKqOtA2xzSVkapcl3ESn61fLFjxTLbTBPfnzBlMBj+NOJnI0fD6BdPDfxEnNaVRwQR28eyMY\nX0qStDMKKKKACiiqOoanFZjb9+Q9FB6fWgCzcXEVtGXlcKO3vWL/AGk97MyY2oOQKy7u9lupd8rZ\nPYDoKLKTZcZPQikxo0pE7ioj5i9Ks5BFMNZmqK/mS09QzfeNSHFKozSGCrgH6VXtNXktrlw43pn8\nRVpyFQisSYATuV6Zq4ozkztbeeO4iEkbZU1JXK6NqJtZxG5HlOec9veuqBDAEHINWQFFFFAGHfa7\n8hS3Ugn+I1gvIWJJOSepNBNRtQAhPzZpysQcio6KANW1vAVCueas+Yh71h7sCpYpSeKmxV2a5Ket\nI06J3rNeVgKhM7Hg801FD5mXbi8JBCmqJJzSBs0GqtYhsTPz1qafq09qVVm3xD+E9h7VlH71PpCO\n3tbuG7j3wtn1HcUVxqSvH91iKKdhn//Z","docno":"421181199208041914"}
     */

    private ListBean list;


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

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


    public String getCodeimgurl() {
        return codeimgurl;
    }

    public void setCodeimgurl(String codeimgurl) {
        this.codeimgurl = codeimgurl;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * rmk :
         * rpmsno : 1014
         * address : 湖北省麻城市宋埠镇彭店村三组枣树垸33号
         * nation : 汉族
         * name : 张浩
         * birth : 1992-08-04
         * tel :
         * pic : /9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABsSFBcUERsXFhceHBsgKEIrKCUlKFE6PTBCYFVlZF9V
         * XVtqeJmBanGQc1tdhbWGkJ6jq62rZ4C8ybqmx5moq6T/2wBDARweHigjKE4rK06kbl1upKSkpKSk
         * pKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKT/wAARCAB+AGYDASIA
         * AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA
         * AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3
         * ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm
         * p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA
         * AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx
         * BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK
         * U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3
         * uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDpaKKK
         * ACiignAyaACjp1qjNq9pFuG/cw7AdaxrzVJro4zsT+6D/OgDoxcQl9nmpuHbNSAgjIII9q4wyGrN
         * nqc1ocL8yf3TQB1VFZ1nrEFw2xx5be54NaNABRRRQAUUUUAFFFFAASAMnpWNq+pp5Rht3O49WFN1
         * nUPlMKZHv61ixK0rZJzQCI9tNINXjbjZnFQSoFGRSTKaIBu9TTgT3pucGlJpkDunIrb0nVHDJBO2
         * QeAx6isHcaUOQaBndUVnaJefabXax+ePg/StGgAooooAKrahN5Fszd8YFWayPEcuy3jUdS36UAYE
         * spkJZupq3pqBgaoAZYAVp2CsinipZUSx5RU7TyDVK7hIDADjPFaBlcH/AFYp3yzLtZQKlaGjOfKH
         * j3pFUmtiazQKSOcDiqEcD8lRVXIcSuykUyr8ltJs3be1UnUjNNMlou6Nctb3qEfdY7SK66uEiYrI
         * CDjBrt7aQS28bjuopiJKKKKACsPxN9yICtyqGtW/n2TMOsfzCgDmLRDJMABW0D5KD5eaztKH+lYP
         * pW6UBFQzSJmyXcm5U8v73epItzHJGKt+VmlCDcBUlkc0R8v61QkMkIPlpnBrZdcqM1EYgeRQgKET
         * zug3RjBH5VDd6eTGXUcithExRJjYfpTuS0cgow+O9dppv/IPgz/cFcm8eb18dA1dhaENbRkDAKji
         * rRDViWiiimSFNkQSRsh6EYp1FAHLxRG11BkPGMithDUWrQATR3AHsaVGGAQahmsWWeMVVluRG/K5
         * 96m3gDmq0jxk8mpLsOe/AwNpb6CrEEm8ZxgVUR4B3qykiEYBoCxM2BVW4YhGPsalZ6qXrgQsM4zx
         * TRLKGnwtcO3HLN1rqI1CIqjgAYrJ0RVEkgXkKorXq0ZyfQKKKKZIUUUUAZ+tS+VbBsZOazrCfzIR
         * nqvFaupWhuYGCnnjiqr2SWlqu0fN3NJlpjWO4YzUBiUckbvrSh+eakBU9azNEyNUjPHlgVIiKnKm
         * nfJTXKqOtA2xzSVkapcl3ESn61fLFjxTLbTBPfnzBlMBj+NOJnI0fD6BdPDfxEnNaVRwQR28eyMY
         * X0qStDMKKKKACiiqOoanFZjb9+Q9FB6fWgCzcXEVtGXlcKO3vWL/AGk97MyY2oOQKy7u9lupd8rZ
         * PYDoKLKTZcZPQikxo0pE7ioj5i9Ks5BFMNZmqK/mS09QzfeNSHFKozSGCrgH6VXtNXktrlw43pn8
         * RVpyFQisSYATuV6Zq4ozkztbeeO4iEkbZU1JXK6NqJtZxG5HlOec9veuqBDAEHINWQFFFFAGHfa7
         * 8hS3Ugn+I1gvIWJJOSepNBNRtQAhPzZpysQcio6KANW1vAVCueas+Yh71h7sCpYpSeKmxV2a5Ket
         * I06J3rNeVgKhM7Hg801FD5mXbi8JBCmqJJzSBs0GqtYhsTPz1qafq09qVVm3xD+E9h7VlH71PpCO
         * 3tbuG7j3wtn1HcUVxqSvH91iKKdhn//Z
         * docno : 421181199208041914
         */

        private String rmk;
        private String rpmsno;
        private String address;
        private String nation;
        private String name;
        private String birth;
        private String tel;
        private String pic;
        private String gender;
        private String docno;

        public String getRmk() {
            return rmk;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "rmk='" + rmk + '\'' +
                    ", rpmsno='" + rpmsno + '\'' +
                    ", address='" + address + '\'' +
                    ", nation='" + nation + '\'' +
                    ", name='" + name + '\'' +
                    ", birth='" + birth + '\'' +
                    ", tel='" + tel + '\'' +
                    ", pic='" + pic + '\'' +
                    ", gender='" + gender + '\'' +
                    ", docno='" + docno + '\'' +
                    '}';
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setRmk(String rmk) {
            this.rmk = rmk;
        }

        public String getRpmsno() {
            return rpmsno;
        }

        public void setRpmsno(String rpmsno) {
            this.rpmsno = rpmsno;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getDocno() {
            return docno;
        }

        public void setDocno(String docno) {
            this.docno = docno;
        }


    }
}
