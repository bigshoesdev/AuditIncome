package com.system.software.solutions.sts.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LCD")
public class LCD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERIAL")
    private String serial;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "SCREENSIZE")
    private String screenSize;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "SCREEN_CONDITION")
    private String screenCondition;

    @Column(name = "STAND")
    private String stand;

    @Column(name = "INT_SERIAL")
    private String intSerial;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "CUSTOMER_ASSET")
    private String customerAsset;

    @Column(name = "LOT_ID")
    private String lotId;

    @Column(name = "PALETTE")
    private String palette;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "BOX")
    private String box;

    @Column(name = "NOTES")
    private String notes;

//    @Type(type = "timestamp")
//    @Column(name = "DATE_IN", nullable = true)
//    private Date dateIn;
    @Column(name = "DATE_IN")
    private String dateIn;
    
    @Column(name = "AVAILABLE")
    private String available;

    @Column(name = "HOURS")
    private String hours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getScreenCondition() {
        return screenCondition;
    }

    public void setScreenCondition(String screenCondition) {
        this.screenCondition = screenCondition;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public String getIntSerial() {
        return intSerial;
    }

    public void setIntSerial(String intSerial) {
        this.intSerial = intSerial;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerAsset() {
        return customerAsset;
    }

    public void setCustomerAsset(String customerAsset) {
        this.customerAsset = customerAsset;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getPalette() {
        return palette;
    }

    public void setPalette(String palette) {
        this.palette = palette;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * @return the hours
     */
    public String getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(String hours) {
        this.hours = hours;
    }

}
