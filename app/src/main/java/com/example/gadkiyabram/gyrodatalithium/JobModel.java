package com.example.gadkiyabram.gyrodatalithium;

public class JobModel {
    private int _id;
    private String jobN;
    private String client;
    private String gdp;
    private String modem;
    private String modemVer;
    private String bullplug;
    private String circHrs;
    private String battery;
    private String maxTemp;
    private String survEng1;
    private String survEng2;
    private String engArr1;
    private String engArr2;
    private String engLft1;
    private String engLft2;
    private String container;
    private String contArr;
    private String contLft;
    private String rig;
    private String issues;
    private String comments;
    private String jobCreated;
    private String jobUpdated;

    public JobModel(int _id,
                    String jobCreated,
                    String jobUpdated) {
        this._id = _id;
        this.jobCreated = jobCreated;
        this.jobUpdated = jobUpdated;
    }

    public JobModel(int _id,
                    String jobN,
                    String client,
                    String gdp,
                    String modem,
                    String modemVer,
                    String bullplug,
                    String circHrs,
                    String battery,
                    String maxTemp,
                    String survEng1,
                    String survEng2,
                    String engArr1,
                    String engArr2,
                    String engLft1,
                    String engLft2,
                    String container,
                    String contArr,
                    String contLft,
                    String comments,
                    String rig,
                    String issues) {
        this._id = _id;
        this.jobN = jobN;
        this.client = client;
        this.gdp = gdp;
        this.modem = modem;
        this.modemVer = modemVer;
        this.bullplug = bullplug;
        this.circHrs = circHrs;
        this.battery = battery;
        this.maxTemp = maxTemp;
        this.survEng1 = survEng1;
        this.survEng2 = survEng2;
        this.engArr1 = engArr1;
        this.engArr2 = engArr2;
        this.engLft1 = engLft1;
        this.engLft2 = engLft2;
        this.container = container;
        this.contArr = contArr;
        this.contLft = contLft;
        this.comments = comments;
        this.rig = rig;
        this.issues = issues;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getJobN() {
        return jobN;
    }

    public void setJobN(String jobN) {
        this.jobN = jobN;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTool() {
        return gdp;
    }

    public void setTool(String tool) {
        this.gdp = tool;
    }

    public String getModem() {
        return modem;
    }

    public void setModem(String modem) {
        this.modem = modem;
    }

    public String getModemVer() {
        return modemVer;
    }

    public void setModemVer(String modemVer){this.modemVer = modemVer;}

    public String getBullplug() {
        return bullplug;
    }

    public void setBullplug(String bullplug) {
        this.bullplug = bullplug;
    }

    public String getCircHrs() {
        return circHrs;
    }

    public void setCircHrs(String circHrs) {
        this.circHrs = circHrs;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getSurvEng1() {
        return survEng1;
    }

    public void setSurvEng1(String survEng1) {
        this.survEng1 = survEng1;
    }

    public String getSurvEng2() {
        return survEng2;
    }

    public void setSurvEng2(String survEng2) {
        this.survEng2 = survEng2;
    }

    public String getEngArr1() {
        return engArr1;
    }

    public void setEngArr1(String engArr1) {
        this.engArr1 = engArr1;
    }

    public String getEngArr2() {
        return engArr2;
    }

    public void setEngArr2(String engArr2) {
        this.engArr2 = engArr2;
    }

    public String getEngLft1() {
        return engLft1;
    }

    public void setEngLft1(String engLft1) {
        this.engLft1 = engLft1;
    }

    public String getEngLft2() {
        return engLft2;
    }

    public void setEngLft2(String engLft2) {
        this.engLft2 = engLft2;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getContArr() {
        return contArr;
    }

    public void setContArr(String contArr) {
        this.contArr = contArr;
    }

    public String getContLft() {
        return contLft;
    }

    public void setContLft(String contLft) {
        this.contLft = contLft;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRig() {
        return rig;
    }

    public void setRig(String rig) {
        this.rig = rig;
    }

    public String getIssues() { return issues; }

    public void setIssues(String issues) { this.issues = issues; }

    public String getJobCreated() {
        return jobCreated;
    }

    public void setJobCreated(String jobCreated) {
        this.jobCreated = jobCreated;
    }

    public String getJobUpdated() {
        return jobUpdated;
    }

    public void setJobUpdated(String jobUpdated) {
        this.jobUpdated= jobUpdated;
    }
}
