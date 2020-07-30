package com.example.gadkiyabram.gyrodatalithium;

public class BatteryModel {

    private int _id;
    private String boxN;
    private String BatteryCondition;
    private String SerialOne;
    private String serNum2;
    private String serNum3;
    private String CCD;
    private String Invoice;
    private String date;
    private String BatteryStatus;
    private String container;
    private String comment;
    private String batteryCreated;
    private String batteryUpdated;

    public BatteryModel(int id,
                        String batteryCreated,
                        String batteryUpdated){
        this._id = id;
        this.batteryCreated = batteryCreated;
        this.batteryUpdated = batteryUpdated;
    }

    public BatteryModel(int id,
                        String boxN,
                        String condition,
                        String serNum1,
                        String serNum2,
                        String serNum3,
                        String ccd,
                        String invoice,
                        String date,
                        String status,
                        String container,
                        String comment){
        this._id = id;
        this.boxN = boxN;
        this.BatteryCondition = condition;
        this.SerialOne = serNum1;
        this.serNum2 = serNum2;
        this.serNum3 = serNum3;
        this.CCD = ccd;
        this.Invoice = invoice;
        this.date = date;
        this.BatteryStatus = status;
        this.container = container;
        this.comment = comment;
    }

    public void set_id(int _id){
        this._id = _id;
    }

    public int get_id(){
        return _id;
    }

    public String getBoxN() {
        return boxN;
    }

    public void setBoxN(String boxN) { this.boxN = boxN; }

    public String getCondition() { return BatteryCondition; }

    public void setCondition(String condition) {
        this.BatteryCondition = condition;
    }

    public String getSerNum1() {
        return SerialOne;
    }

    public void setSerNum1(String serNum1) {
        this.SerialOne = serNum1;
    }

    public String getSerNum2() {
        return serNum2;
    }

    public void setSerNum2(String serNum2) {
        this.serNum2 = serNum2;
    }

    public String getSerNum3() {
        return serNum3;
    }

    public void setSerNum3(String serNum3) {
        this.serNum3 = serNum3;
    }

    public String getCCD() { return CCD; }

    public void setCCD(String ccd){ this.CCD = ccd; }

    public String getInvoice() { return Invoice; }

    public void setInvoice(String invoice){ this.CCD = invoice; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return BatteryStatus;
    }

    public void setStatus(String status) {
        this.BatteryStatus = status;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBatteryCreated() {
        return batteryCreated;
    }

    public void setBatteryCreated(String batteryCreated) {
        this.batteryCreated = batteryCreated;
    }

    public String getBatteryUpdated() {
        return batteryUpdated;
    }

    public void setBatteryUpdated(String batteryUpdated) { this.batteryUpdated = batteryUpdated;
    }
}
