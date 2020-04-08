package com.example.gadkiyabram.gyrodatalithium;

public class ToolModel {

    private int _id;
    private String itemName;
    private String asset;
    private String arrived;
    private float circHrs;
    private String invoice;
    private String ccdNum;
    private String nameRus;
    private String positionCCD;
    private String location;
    private String boxDesc;
    private String container;
    private String comment;
    private String itemImage;

    public ToolModel(int _id,
                     String itemName,
                     String asset,
                     String arrived,
                     float circHrs,
                     String invoice,
                     String ccdNum,
                     String nameRus,
                     String positionCCD,
                     String location,
                     String boxDesc,
                     String container,
                     String comment) {
        this._id = _id;
        this.itemName = itemName;
        this.asset = asset;
        this.arrived = arrived;
        this.circHrs = circHrs;
        this.invoice = invoice;
        this.ccdNum = ccdNum;
        this.nameRus = nameRus;
        this.positionCCD = positionCCD;
        this.location = location;
        this.boxDesc = boxDesc;
        this.container = container;
        this.comment = comment;
    }

    public ToolModel(int _id,
                     String itemName,
                     String asset,
                     String arrived,
                     float circHrs,
                     String invoice,
                     String ccdNum,
                     String nameRus,
                     String positionCCD,
                     String location,
                     String boxDesc,
                     String container,
                     String comment,
                     String itemImage) {
        this._id = _id;
        this.itemName = itemName;
        this.asset = asset;
        this.arrived = arrived;
        this.circHrs = circHrs;
        this.invoice = invoice;
        this.ccdNum = ccdNum;
        this.nameRus = nameRus;
        this.positionCCD = positionCCD;
        this.location = location;
        this.boxDesc = boxDesc;
        this.container = container;
        this.comment = comment;
        this.itemImage = itemImage;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getArrived() {
        return arrived;
    }

    public void setArrived(String arrived) {
        this.arrived = arrived;
    }

    public float getCircHrs(){
        return circHrs;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCcdNum() {
        return ccdNum;
    }

    public void setCcdNum(String ccdNum) {
        this.ccdNum = ccdNum;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getPositionCCD() {
        return positionCCD;
    }

    public void setPositionCCD(String positionCCD) {
        this.positionCCD = positionCCD;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBoxDesc() {
        return boxDesc;
    }

    public void setBoxDesc(String boxDesc) {
        this.boxDesc = boxDesc;
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

    public String getItemImage() { return itemImage; }

    public void setItemImage(String itemImage){ this.itemImage = itemImage; }
}
