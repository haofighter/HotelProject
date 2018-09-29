package com.sun.hotelproject.entity;

//订单的入住信息
public class OrderDetalieInfo {


    /**
     * result : success
     * guetlist : {"MCHID":"100100100101","ORDERID":"PMS201808311451144124","NATION":"汉族","ADDRESS":"武汉市江夏区乌龙泉街群建村董家湾25号                ","GENDER":"男","DOCNO":"420115199607060931","BIRTH":"1996-07-06","PHOTO":"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABsSFBcUERsXFhceHBsgKEIrKCUlKFE6PTBCYFVlZF9V\nXVtqeJmBanGQc1tdhbWGkJ6jq62rZ4C8ybqmx5moq6T/2wBDARweHigjKE4rK06kbl1upKSkpKSk\npKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKT/wAARCAB+AGYDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDpaKKK\nQBRRUVxPHbQtLKcKtMCWmmRFOC6g+5rlr7Xri4ysX7pPbqfxrNaZnOWYk+pNOwHdebH/AM9E/wC+\nhSq6t91gfoa4MtmnwXMtu+6KRkI9DRYR3dFYGl69ubyrxgPR+n51vAggEdDSsMWiiikAUUUUAFFF\nFADZJFiQu7BVAySa5rWdUF5+5hH7oc57k0mvX7T3DQRt+6Tg47mq1na71DkU72GkVUgZv4al+wv6\nVrxQqvaptgqOdl8pgGxk7VE9vKnJFdEUFRSRAjpRzsOU58e45ro9B1NpMW05yQPlYn9Kyru1wSyi\nqccjRSAqcMDkVadyGrHfUVXsLkXdnHMOpHP1qxQIKKKKQBUVy/l28j+inpUtMlTzImQjO4EUwOH5\nkk9STW3boEjVR2FZkMDJemNhgqcGtZlbbhetTLcuI9eTxUhU1XWzkA3faGB9OKfGZUOHff71Ni7k\nhU0xgamZsDJqq4uJD+7cKv0zSsBHIuTzWTexBJcgda02gnjOTJv+tVr1N0YYiqWjIZr+GiTYsPRq\n16yfDaFbFjjhm4rWq3uQFFFFIApsjbI2b0FOpsi70ZfUUAYbxbrsT9Cw5qfbleDio33CQqc/LxU0\nRGKk1Kk1q7gnzXzn16VJDGyKAzE47mrhAqFzzgUAJKcqBVaeOZh+6kK/Sp5AcZp0PzLz1pDZWWKY\nEZcsPem3cW6Igdc1fYACqsrYzTJL+kMhtBGi7fLOD7mr1UNIB+zsxH3mq/VEPcKKKKBBRRRQBn38\nO1vMUHB61XjOK1nUMpU9DWSRtYj0OKllxZNu4qGZSQdrFc9xS54qCV5M4A/HNItA6sVCiRhjv61L\nAdoOTkmqhMp7/rU0DP8AxAUATyNUITzHC9ycU5zVnTE3O8hHTgGmiXoX4kEcaovQDFPooqjMKKKK\nACiiigArO1JRG6uOA3B+taBIAyTgVmajPDcKEjcPtPOD0oew1uQK4I4NKRkVSbfGcr0pRdkD5hg1\nmaFnYM9KCdtVvtg96Y07P0phcshi7hR1JxW3bxCGFUHUdawbTPnx59RXR04kSCiiiqJCiiigAopC\ncdapXOoxruihYNLgn2FNK4Gd4ivmx9miY4/jx39qrWKqsYC5565rOknaSY56FsmtO2OQDSnpoVFd\nSyyAioWhU9as54pCB3rMsqGFB0FIEFWGUU3bQBCxKAkHBHIrU0XUftcOyU/vV6+4rKueI2qrZyPb\nlHVgGFXBXJkdlRWfa6rFKdsnyN69qvI6uu5GDD1FU00ZjqKKKQzlptQub2UKThCcbAcD8ajusrdF\nRtUBei9KzhI5kGWJ5q/aQrNdSBiRhM8V0adCTOY7XP1rTsJeitkH3qk8Ko5JJPNRpdsZ9x7dMdqy\nnEuL0Oh3c0E1TtLvzn2Ffoau7RisWrGgzk0hJAxUqqKRlHJpDKN2TjHasxyRISDn3q9qDZBA4xWY\nWwwFbQWlzJvWxet7rZKvyhsqQQatWOoNBK4RMegycflWXD/rhViPi7fjpmtL3Jt2N6LxDbkfvI3U\n98YNFcy3DkUUuVDsz//Z","DOCTYPE":"1","GUEST_ID":"110d87d74a534127b3973cfb0c7ebb88","NAME":"王亚东"}
     * rescode : 0000
     * orderlist : {"PAYSTATUS":"1","SERNUMBER":"YvMK1azqEei5QQAFKcIb1g","PAYWAY":"141","CARDNUM":"1","DUTYPMSNO":"1","HPMSNO":"BZTHEF","PMSMSG":"success-入住成功","OUTIME":"2018-09-01 12:00:00","ORDERID":"PMS201808311451144124","MCHID":"100100100101","OPMSNO":"","RTPMSNO":"HHSC","INTYPE":"1","OPERATIONTIME":"2018-08-31 14:52:11","TYPE":"01","RPMSNO":"8302","PAYPRICE":"0.01","BREAKNUM":"0002","ORDERTYPE":"6","ARPRICE":0.01,"DEVNO":"C8W7KYG7SY","PAYTYPE":"3","TRADENO":"50ced6027e3b41b1888831265e6ad692","LOCKSIGN":"0","DEALPRICE":0.01,"INORDERPMSNO":"13378","PMSTUATS":"0000","INDATE":"2018-08-31","OLDPRICE":0.01,"RMK":"","JOBPMSNO":"","INTIME":"2018-08-31 14:51:14","CHECKINORDER_ID":"50ced6027e3b41b1888831265e6ad692","TRADETYPE":"","TEL":"","TEAM":"1","RPRICENO":"RAC"}
     */

    private String result;
    private GuetlistBean guetlist;
    private String rescode;
    private OrderlistBean orderlist;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public GuetlistBean getGuetlist() {
        return guetlist;
    }

    public void setGuetlist(GuetlistBean guetlist) {
        this.guetlist = guetlist;
    }

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public OrderlistBean getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(OrderlistBean orderlist) {
        this.orderlist = orderlist;
    }

    public static class GuetlistBean {
        /**
         * MCHID : 100100100101
         * ORDERID : PMS201808311451144124
         * NATION : 汉族
         * ADDRESS : 武汉市江夏区乌龙泉街群建村董家湾25号
         * GENDER : 男
         * DOCNO : 420115199607060931
         * BIRTH : 1996-07-06
         * PHOTO : /9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABsSFBcUERsXFhceHBsgKEIrKCUlKFE6PTBCYFVlZF9V
         XVtqeJmBanGQc1tdhbWGkJ6jq62rZ4C8ybqmx5moq6T/2wBDARweHigjKE4rK06kbl1upKSkpKSk
         pKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKT/wAARCAB+AGYDASIA
         AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA
         AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3
         ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm
         p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA
         AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx
         BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK
         U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3
         uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDpaKKK
         QBRRUVxPHbQtLKcKtMCWmmRFOC6g+5rlr7Xri4ysX7pPbqfxrNaZnOWYk+pNOwHdebH/AM9E/wC+
         hSq6t91gfoa4MtmnwXMtu+6KRkI9DRYR3dFYGl69ubyrxgPR+n51vAggEdDSsMWiiikAUUUUAFFF
         FADZJFiQu7BVAySa5rWdUF5+5hH7oc57k0mvX7T3DQRt+6Tg47mq1na71DkU72GkVUgZv4al+wv6
         VrxQqvaptgqOdl8pgGxk7VE9vKnJFdEUFRSRAjpRzsOU58e45ro9B1NpMW05yQPlYn9Kyru1wSyi
         qccjRSAqcMDkVadyGrHfUVXsLkXdnHMOpHP1qxQIKKKKQBUVy/l28j+inpUtMlTzImQjO4EUwOH5
         kk9STW3boEjVR2FZkMDJemNhgqcGtZlbbhetTLcuI9eTxUhU1XWzkA3faGB9OKfGZUOHff71Ni7k
         hU0xgamZsDJqq4uJD+7cKv0zSsBHIuTzWTexBJcgda02gnjOTJv+tVr1N0YYiqWjIZr+GiTYsPRq
         16yfDaFbFjjhm4rWq3uQFFFFIApsjbI2b0FOpsi70ZfUUAYbxbrsT9Cw5qfbleDio33CQqc/LxU0
         RGKk1Kk1q7gnzXzn16VJDGyKAzE47mrhAqFzzgUAJKcqBVaeOZh+6kK/Sp5AcZp0PzLz1pDZWWKY
         EZcsPem3cW6Igdc1fYACqsrYzTJL+kMhtBGi7fLOD7mr1UNIB+zsxH3mq/VEPcKKKKBBRRRQBn38
         O1vMUHB61XjOK1nUMpU9DWSRtYj0OKllxZNu4qGZSQdrFc9xS54qCV5M4A/HNItA6sVCiRhjv61L
         AdoOTkmqhMp7/rU0DP8AxAUATyNUITzHC9ycU5zVnTE3O8hHTgGmiXoX4kEcaovQDFPooqjMKKKK
         ACiiigArO1JRG6uOA3B+taBIAyTgVmajPDcKEjcPtPOD0oew1uQK4I4NKRkVSbfGcr0pRdkD5hg1
         maFnYM9KCdtVvtg96Y07P0phcshi7hR1JxW3bxCGFUHUdawbTPnx59RXR04kSCiiiqJCiiigAopC
         cdapXOoxruihYNLgn2FNK4Gd4ivmx9miY4/jx39qrWKqsYC5565rOknaSY56FsmtO2OQDSnpoVFd
         SyyAioWhU9as54pCB3rMsqGFB0FIEFWGUU3bQBCxKAkHBHIrU0XUftcOyU/vV6+4rKueI2qrZyPb
         lHVgGFXBXJkdlRWfa6rFKdsnyN69qvI6uu5GDD1FU00ZjqKKKQzlptQub2UKThCcbAcD8ajusrdF
         RtUBei9KzhI5kGWJ5q/aQrNdSBiRhM8V0adCTOY7XP1rTsJeitkH3qk8Ko5JJPNRpdsZ9x7dMdqy
         nEuL0Oh3c0E1TtLvzn2Ffoau7RisWrGgzk0hJAxUqqKRlHJpDKN2TjHasxyRISDn3q9qDZBA4xWY
         WwwFbQWlzJvWxet7rZKvyhsqQQatWOoNBK4RMegycflWXD/rhViPi7fjpmtL3Jt2N6LxDbkfvI3U
         98YNFcy3DkUUuVDsz//Z
         * DOCTYPE : 1
         * GUEST_ID : 110d87d74a534127b3973cfb0c7ebb88
         * NAME : 王亚东
         */

        private String MCHID;
        private String ORDERID;
        private String NATION;
        private String ADDRESS;
        private String GENDER;
        private String DOCNO;
        private String BIRTH;
        private String PHOTO;
        private String DOCTYPE;
        private String GUEST_ID;
        private String NAME;

        public String getMCHID() {
            return MCHID;
        }

        public void setMCHID(String MCHID) {
            this.MCHID = MCHID;
        }

        public String getORDERID() {
            return ORDERID;
        }

        public void setORDERID(String ORDERID) {
            this.ORDERID = ORDERID;
        }

        public String getNATION() {
            return NATION;
        }

        public void setNATION(String NATION) {
            this.NATION = NATION;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getGENDER() {
            return GENDER;
        }

        public void setGENDER(String GENDER) {
            this.GENDER = GENDER;
        }

        public String getDOCNO() {
            return DOCNO;
        }

        public void setDOCNO(String DOCNO) {
            this.DOCNO = DOCNO;
        }

        public String getBIRTH() {
            return BIRTH;
        }

        public void setBIRTH(String BIRTH) {
            this.BIRTH = BIRTH;
        }

        public String getPHOTO() {
            return PHOTO;
        }

        public void setPHOTO(String PHOTO) {
            this.PHOTO = PHOTO;
        }

        public String getDOCTYPE() {
            return DOCTYPE;
        }

        public void setDOCTYPE(String DOCTYPE) {
            this.DOCTYPE = DOCTYPE;
        }

        public String getGUEST_ID() {
            return GUEST_ID;
        }

        public void setGUEST_ID(String GUEST_ID) {
            this.GUEST_ID = GUEST_ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }
    }

    public static class OrderlistBean {
        /**
         * PAYSTATUS : 1
         * SERNUMBER : YvMK1azqEei5QQAFKcIb1g
         * PAYWAY : 141
         * CARDNUM : 1
         * DUTYPMSNO : 1
         * HPMSNO : BZTHEF
         * PMSMSG : success-入住成功
         * OUTIME : 2018-09-01 12:00:00
         * ORDERID : PMS201808311451144124
         * MCHID : 100100100101
         * OPMSNO :
         * RTPMSNO : HHSC
         * INTYPE : 1
         * OPERATIONTIME : 2018-08-31 14:52:11
         * TYPE : 01
         * RPMSNO : 8302
         * PAYPRICE : 0.01
         * BREAKNUM : 0002
         * ORDERTYPE : 6
         * ARPRICE : 0.01
         * DEVNO : C8W7KYG7SY
         * PAYTYPE : 3
         * TRADENO : 50ced6027e3b41b1888831265e6ad692
         * LOCKSIGN : 0
         * DEALPRICE : 0.01
         * INORDERPMSNO : 13378
         * PMSTUATS : 0000
         * INDATE : 2018-08-31
         * OLDPRICE : 0.01
         * RMK :
         * JOBPMSNO :
         * INTIME : 2018-08-31 14:51:14
         * CHECKINORDER_ID : 50ced6027e3b41b1888831265e6ad692
         * TRADETYPE :
         * TEL :
         * TEAM : 1
         * RPRICENO : RAC
         */

        private String PAYSTATUS;
        private String SERNUMBER;
        private String PAYWAY;
        private String CARDNUM;
        private String DUTYPMSNO;
        private String HPMSNO;
        private String PMSMSG;
        private String OUTIME;
        private String ORDERID;
        private String MCHID;
        private String OPMSNO;
        private String RTPMSNO;
        private String INTYPE;
        private String OPERATIONTIME;
        private String TYPE;
        private String RPMSNO;
        private String PAYPRICE;
        private String BREAKNUM;
        private String ORDERTYPE;
        private double ARPRICE;
        private String DEVNO;
        private String PAYTYPE;
        private String TRADENO;
        private String LOCKSIGN;
        private double DEALPRICE;
        private String INORDERPMSNO;
        private String PMSTUATS;
        private String INDATE;
        private double OLDPRICE;
        private String RMK;
        private String JOBPMSNO;
        private String INTIME;
        private String CHECKINORDER_ID;
        private String TRADETYPE;
        private String TEL;
        private String TEAM;
        private String RPRICENO;

        public String getPAYSTATUS() {
            return PAYSTATUS;
        }

        public void setPAYSTATUS(String PAYSTATUS) {
            this.PAYSTATUS = PAYSTATUS;
        }

        public String getSERNUMBER() {
            return SERNUMBER;
        }

        public void setSERNUMBER(String SERNUMBER) {
            this.SERNUMBER = SERNUMBER;
        }

        public String getPAYWAY() {
            return PAYWAY;
        }

        public void setPAYWAY(String PAYWAY) {
            this.PAYWAY = PAYWAY;
        }

        public String getCARDNUM() {
            return CARDNUM;
        }

        public void setCARDNUM(String CARDNUM) {
            this.CARDNUM = CARDNUM;
        }

        public String getDUTYPMSNO() {
            return DUTYPMSNO;
        }

        public void setDUTYPMSNO(String DUTYPMSNO) {
            this.DUTYPMSNO = DUTYPMSNO;
        }

        public String getHPMSNO() {
            return HPMSNO;
        }

        public void setHPMSNO(String HPMSNO) {
            this.HPMSNO = HPMSNO;
        }

        public String getPMSMSG() {
            return PMSMSG;
        }

        public void setPMSMSG(String PMSMSG) {
            this.PMSMSG = PMSMSG;
        }

        public String getOUTIME() {
            return OUTIME;
        }

        public void setOUTIME(String OUTIME) {
            this.OUTIME = OUTIME;
        }

        public String getORDERID() {
            return ORDERID;
        }

        public void setORDERID(String ORDERID) {
            this.ORDERID = ORDERID;
        }

        public String getMCHID() {
            return MCHID;
        }

        public void setMCHID(String MCHID) {
            this.MCHID = MCHID;
        }

        public String getOPMSNO() {
            return OPMSNO;
        }

        public void setOPMSNO(String OPMSNO) {
            this.OPMSNO = OPMSNO;
        }

        public String getRTPMSNO() {
            return RTPMSNO;
        }

        public void setRTPMSNO(String RTPMSNO) {
            this.RTPMSNO = RTPMSNO;
        }

        public String getINTYPE() {
            return INTYPE;
        }

        public void setINTYPE(String INTYPE) {
            this.INTYPE = INTYPE;
        }

        public String getOPERATIONTIME() {
            return OPERATIONTIME;
        }

        public void setOPERATIONTIME(String OPERATIONTIME) {
            this.OPERATIONTIME = OPERATIONTIME;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getRPMSNO() {
            return RPMSNO;
        }

        public void setRPMSNO(String RPMSNO) {
            this.RPMSNO = RPMSNO;
        }

        public String getPAYPRICE() {
            return PAYPRICE;
        }

        public void setPAYPRICE(String PAYPRICE) {
            this.PAYPRICE = PAYPRICE;
        }

        public String getBREAKNUM() {
            return BREAKNUM;
        }

        public void setBREAKNUM(String BREAKNUM) {
            this.BREAKNUM = BREAKNUM;
        }

        public String getORDERTYPE() {
            return ORDERTYPE;
        }

        public void setORDERTYPE(String ORDERTYPE) {
            this.ORDERTYPE = ORDERTYPE;
        }

        public double getARPRICE() {
            return ARPRICE;
        }

        public void setARPRICE(double ARPRICE) {
            this.ARPRICE = ARPRICE;
        }

        public String getDEVNO() {
            return DEVNO;
        }

        public void setDEVNO(String DEVNO) {
            this.DEVNO = DEVNO;
        }

        public String getPAYTYPE() {
            return PAYTYPE;
        }

        public void setPAYTYPE(String PAYTYPE) {
            this.PAYTYPE = PAYTYPE;
        }

        public String getTRADENO() {
            return TRADENO;
        }

        public void setTRADENO(String TRADENO) {
            this.TRADENO = TRADENO;
        }

        public String getLOCKSIGN() {
            return LOCKSIGN;
        }

        public void setLOCKSIGN(String LOCKSIGN) {
            this.LOCKSIGN = LOCKSIGN;
        }

        public double getDEALPRICE() {
            return DEALPRICE;
        }

        public void setDEALPRICE(double DEALPRICE) {
            this.DEALPRICE = DEALPRICE;
        }

        public String getINORDERPMSNO() {
            return INORDERPMSNO;
        }

        public void setINORDERPMSNO(String INORDERPMSNO) {
            this.INORDERPMSNO = INORDERPMSNO;
        }

        public String getPMSTUATS() {
            return PMSTUATS;
        }

        public void setPMSTUATS(String PMSTUATS) {
            this.PMSTUATS = PMSTUATS;
        }

        public String getINDATE() {
            return INDATE;
        }

        public void setINDATE(String INDATE) {
            this.INDATE = INDATE;
        }

        public double getOLDPRICE() {
            return OLDPRICE;
        }

        public void setOLDPRICE(double OLDPRICE) {
            this.OLDPRICE = OLDPRICE;
        }

        public String getRMK() {
            return RMK;
        }

        public void setRMK(String RMK) {
            this.RMK = RMK;
        }

        public String getJOBPMSNO() {
            return JOBPMSNO;
        }

        public void setJOBPMSNO(String JOBPMSNO) {
            this.JOBPMSNO = JOBPMSNO;
        }

        public String getINTIME() {
            return INTIME;
        }

        public void setINTIME(String INTIME) {
            this.INTIME = INTIME;
        }

        public String getCHECKINORDER_ID() {
            return CHECKINORDER_ID;
        }

        public void setCHECKINORDER_ID(String CHECKINORDER_ID) {
            this.CHECKINORDER_ID = CHECKINORDER_ID;
        }

        public String getTRADETYPE() {
            return TRADETYPE;
        }

        public void setTRADETYPE(String TRADETYPE) {
            this.TRADETYPE = TRADETYPE;
        }

        public String getTEL() {
            return TEL;
        }

        public void setTEL(String TEL) {
            this.TEL = TEL;
        }

        public String getTEAM() {
            return TEAM;
        }

        public void setTEAM(String TEAM) {
            this.TEAM = TEAM;
        }

        public String getRPRICENO() {
            return RPRICENO;
        }

        public void setRPRICENO(String RPRICENO) {
            this.RPRICENO = RPRICENO;
        }
    }
}
