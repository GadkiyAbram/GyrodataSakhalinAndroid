package com.example.gadkiyabram.gyrodatalithium;

public class BatteryDetails {

    private int _id;
    private int boxN;
    private String condition;
    private String serNum1;
    private String serNum2;
    private String serNum3;
    private String date;
    private String status;
    private String comment;

    public BatteryDetails(){}

    public BatteryDetails(int _id,
                          int boxN,
                          String condition,
                          String serNum1,
                          String serNum2,
                          String serNum3,
                          String date,
                          String status,
                          String comment){
        this._id = _id;
        this.boxN = boxN;
        this.condition = condition;
        this.serNum1 = serNum1;
        this.serNum2 = serNum2;
        this.serNum3 = serNum3;
        this.date = date;
        this.status = status;
        this.comment = comment;
    }

    public void set_id(int _id){
        this._id = _id;
    }

    public int get_id(){
        return _id;
    }

    public int getBoxN() {
        return boxN;
    }

    public void setBoxN(int boxN) {
        this.boxN = boxN;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSerNum1() {
        return serNum1;
    }

    public void setSerNum1(String serNum1) {
        this.serNum1 = serNum1;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
