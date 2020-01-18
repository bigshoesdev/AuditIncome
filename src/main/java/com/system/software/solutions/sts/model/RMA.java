package com.system.software.solutions.sts.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "RMA")
public class RMA implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERIAL")
    private String serial;

    @Column(name = "ORDERNUMBER")
    private String orderNumber;

    @Column(name = "TECH")
    private String tech;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "INT_SERIAL")
    private String intSerial;

    @Column(name = "DATE_PRODUCED")
    private String dateProduced;

    @Column(name = "DATE_PROCESSED")
    private String dateProcessed;

    @Column(name = "TROUBLE_CODE")
    private String troubleCode;

    @Column(name = "HDDNOTES")
    private String hddNotes;

    @Column(name = "MEMNOTES")
    private String memNotes;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "HDDSIZE")
    private String hddSize;

    @Column(name = "HDDSERIAL")
    private String hddSerial;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "HDDMODEL")
    private String hddModel;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getIntSerial() {
        return intSerial;
    }

    public void setIntSerial(String intSerial) {
        this.intSerial = intSerial;
    }

    public String getDateProduced() {
        return dateProduced;
    }

    public void setDateProduced(String dateProduced) {
        this.dateProduced = dateProduced;
    }

    public String getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(String dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public String getTroubleCode() {
        return troubleCode;
    }

    public void setTroubleCode(String troubleCode) {
        this.troubleCode = troubleCode;
    }

    public String getHddNotes() {
        return hddNotes;
    }

    public void setHddNotes(String hddNotes) {
        this.hddNotes = hddNotes;
    }

    public String getMemNotes() {
        return memNotes;
    }

    public void setMemNotes(String memNotes) {
        this.memNotes = memNotes;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getHddSize() {
        return hddSize;
    }

    public void setHddSize(String hddSize) {
        this.hddSize = hddSize;
    }

    public String getHddSerial() {
        return hddSerial;
    }

    public void setHddSerial(String hddSerial) {
        this.hddSerial = hddSerial;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHddModel() {
        return hddModel;
    }

    public void setHddModel(String hddModel) {
        this.hddModel = hddModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
