package com.sun.hotelproject.entity;

/**
 * 作者: Tangren on 2017-09-08
 * 包名：szxb.com.commonbus.entity
 * 邮箱：996489865@qq.com
 * TODO:刷码信息
 */

public class PosRecord {

    private String open_id;
    private String mch_trx_id;
    private Long order_time;
    private int total_fee;
    private int pay_fee;
    private String order_desc;
    private String city_code;
    private String in_station_id;
    private String in_station_name;
    private String out_station_id;
    private String out_station_name;
    private String transaction_id;
    private String status;
    private String status_desc;
    private String record;
    private String bus_no;
    private String bus_line_name;
    private String bus_line_no;
    private String pos_no;
    private String unitno;
    private String driveno;
    private String qr_code;


    /******************************************************************************/
    public String stationName;
    public String stationNo;
    public String fangxiang; //00上行 01下行
    public String stationMark;//00 上车 01下车
    public String zhongzhuanzhanMark;
    public String endNo;
    public String lineNo;//线路号

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getFangxiang() {
        return fangxiang;
    }

    public void setFangxiang(String fangxiang) {
        this.fangxiang = fangxiang;
    }

    public String getStationMark() {
        return stationMark;
    }

    public void setStationMark(String stationMark) {
        this.stationMark = stationMark;
    }

    public String getZhongzhuanzhanMark() {
        return zhongzhuanzhanMark;
    }

    public void setZhongzhuanzhanMark(String zhongzhuanzhanMark) {
        this.zhongzhuanzhanMark = zhongzhuanzhanMark;
    }

    public String getEndNo() {
        return endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getBus_line_no() {
        return bus_line_no;
    }

    public void setBus_line_no(String bus_line_no) {
        this.bus_line_no = bus_line_no;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getUnitno() {
        return unitno;
    }

    public void setUnitno(String unitno) {
        this.unitno = unitno;
    }

    public String getDriveno() {
        return driveno;
    }

    public void setDriveno(String driveno) {
        this.driveno = driveno;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getMch_trx_id() {
        return mch_trx_id;
    }

    public void setMch_trx_id(String mch_trx_id) {
        this.mch_trx_id = mch_trx_id;
    }

    public Long getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Long order_time) {
        this.order_time = order_time;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getPay_fee() {
        return pay_fee;
    }

    public void setPay_fee(int pay_fee) {
        this.pay_fee = pay_fee;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getIn_station_id() {
        return in_station_id;
    }

    public void setIn_station_id(String in_station_id) {
        this.in_station_id = in_station_id;
    }

    public String getIn_station_name() {
        return in_station_name;
    }

    public void setIn_station_name(String in_station_name) {
        this.in_station_name = in_station_name;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getBus_line_name() {
        return bus_line_name;
    }

    public void setBus_line_name(String bus_line_name) {
        this.bus_line_name = bus_line_name;
    }

    public String getPos_no() {
        return pos_no;
    }

    public void setPos_no(String pos_no) {
        this.pos_no = pos_no;
    }

    public String getOut_station_id() {
        return out_station_id;
    }

    public void setOut_station_id(String out_station_id) {
        this.out_station_id = out_station_id;
    }

    public String getOut_station_name() {
        return out_station_name;
    }

    public void setOut_station_name(String out_station_name) {
        this.out_station_name = out_station_name;
    }

    @Override
    public String toString() {
        return "PosRecord{" +
                "open_id='" + open_id + '\'' +
                ", mch_trx_id='" + mch_trx_id + '\'' +
                ", order_time=" + order_time +
                ", total_fee=" + total_fee +
                ", pay_fee=" + pay_fee +
                ", order_desc='" + order_desc + '\'' +
                ", city_code='" + city_code + '\'' +
                ", in_station_id='" + in_station_id + '\'' +
                ", in_station_name='" + in_station_name + '\'' +
                ", out_station_id='" + out_station_id + '\'' +
                ", out_station_name='" + out_station_name + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", status='" + status + '\'' +
                ", status_desc='" + status_desc + '\'' +
                ", record='" + record + '\'' +
                ", bus_no='" + bus_no + '\'' +
                ", bus_line_name='" + bus_line_name + '\'' +
                ", bus_line_no='" + bus_line_no + '\'' +
                ", pos_no='" + pos_no + '\'' +
                ", unitno='" + unitno + '\'' +
                ", driveno='" + driveno + '\'' +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
