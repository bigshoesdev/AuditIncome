package com.system.software.solutions.sts.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "[Audit Live View]")
public class AuditLiveView implements Serializable {

    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(name = "[Check-In Tech]")
    private String checkInTech;

    @Column(name = "[Check-In Date]")
    private String checkInDate;

    @Column(name = "[Audit Tech]")
    private String auditTech;

    @Column(name = "[Audit Date]")
    private String auditDate;

    @Column(name = "[Quantity]")
    private String quantity;

    @Column(name = "[Commodity Code]")
    private String commodityCode;

    @Column(name = "[Category]")
    private String category;

    @Column(name = "[Description]")
    private String description;

    @Column(name = "[Box Open Date]")
    private String boxOpenDate;

    @Column(name = "[Box Close Date]")
    private String boxCloseDate;

    @Column(name = "[STS Serial]")
    private String stsserial;

    @Column(name = "[Available]")
    private String available;

    @Column(name = "[Scrap]")
    private String scrap;

    @Column(name = "[Manufacturer]")
    private String manufacturer;

    @Column(name = "[Serial]")
    private String serial;

    @Column(name = "[Model]")
    private String model;

    @Column(name = "[EMC]")
    private String emc;

    @Column(name = "[Year]")
    private String year;

    @Column(name = "[Form Factor]")
    private String formFactor;

    @Column(name = "[CPU]")
    private String cpu;

    @Column(name = "[CPU Qty]")
    private String cpuqty;

    @Column(name = "[Data Width]")
    private String dataWidth;

    @Column(name = "[CPU Speed]")
    private String cpuspeed;

    @Column(name = "[# Cores]")
    private String cores;

    @Column(name = "[Audit HDD Serial]")
    private String auditHDDSerial;

    @Column(name = "[Audit HDD Model]")
    private String auditHDDModel;

    @Column(name = "[Audit HDD Size]")
    private String auditHDDSize;

    @Column(name = "[Audit HDD Status]")
    private String auditHDDStatus;

    @Column(name = "[Video Adapter]")
    private String videoAdapter;

    @Column(name = "[Screen Size]")
    private String screenSize;

    @Column(name = "[Stand]")
    private String stand;

    @Column(name = "[Screen Condition]")
    private String screenCondition;

    @Column(name = "[Plastic Condition]")
    private String plasticCondition;

    @Column(name = "[Total Memory]")
    private String totalMemory;

    @Column(name = "[# RAM Slots]")
    private String ramslots;

    @Column(name = "[RAM Per Slot]")
    private String ramperSlot;

    @Column(name = "[RAM Part #s]")
    private String ramparts;

    @Column(name = "[RAM Model #]")
    private String rammodel;

    @Column(name = "[Type of RAM]")
    private String typeofRAM;

    @Column(name = "[Capacitor]")
    private String capacitor;

    @Column(name = "[Battery]")
    private String battery;

    @Column(name = "[Battery Health]")
    private String batteryHealth;

    @Column(name = "[A/C Adaptor]")
    private String acAdaptor;

    @Column(name = "[Camera]")
    private String camera;

    @Column(name = "[Connections]")
    private String connections;

    @Column(name = "[DVD Drive]")
    private String dvddrive;

    @Column(name = "[COA Version]")
    private String coaversion;

    @Column(name = "[COA Edition]")
    private String coaedition;

    @Column(name = "[Customer ID #]")
    private String customerID;

    @Column(name = "[Lot ID #]")
    private String lotID;

    @Column(name = "[Customer Asset #]")
    private String customerAsset;

    @Column(name = "[Box #]")
    private String box;

    @Column(name = "[Location]")
    private String location;

    @Column(name = "[Palette]")
    private String palette;

    @Column(name = "[Sold Date]")
    private String soldDate;

    @Column(name = "[Sold HDD Serial]")
    private String soldHDDSerial;

    @Column(name = "[Sold HDD Model]")
    private String soldHDDModel;

    @Column(name = "[Sold HDD Size]")
    private String soldHDDSize;

    @Column(name = "[Sold HDD Smart]")
    private String soldHDDSmart;

    @Column(name = "[Build Order #]")
    private String buildOrder;

    @Column(name = "[Build OS]")
    private String buildOS;

    @Column(name = "[Build SKU]")
    private String buildSKU;

    @Column(name = "[SKU]")
    private String sku;

    @Column(name = "[Notes]")
    private String notes;

    public AuditLiveView() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCheckInTech() {
        return checkInTech;
    }

    public void setCheckInTech(String checkInTech) {
        this.checkInTech = checkInTech;
    }

    public String getCheckInDate() {
        String dateStr = checkInDate;
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
        try {
            Date date = df.parse(dateStr);
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getAuditTech() {
        return auditTech;
    }

    public void setAuditTech(String auditTech) {
        this.auditTech = auditTech;
    }

    public String getAuditDate() {
        String dateStr = auditDate;
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
        try {
            Date date = df.parse(dateStr);
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBoxOpenDate() {
        String dateStr = boxOpenDate;
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        try {
            Date date = df.parse(dateStr);
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }

    public void setBoxOpenDate(String boxOpenDate) {
        this.boxOpenDate = boxOpenDate;
    }

    public String getBoxCloseDate() {
        String dateStr = boxCloseDate;
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        try {
            Date date = df.parse(dateStr);
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }

    public void setBoxCloseDate(String boxCloseDate) {
        this.boxCloseDate = boxCloseDate;
    }

    public String getSTSSerial() {
        return stsserial;
    }

    public void setSTSSerial(String stsserial) {
        this.stsserial = stsserial;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getScrap() {
        return scrap;
    }

    public void setScrap(String scrap) {
        this.scrap = scrap;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEmc() {
        return emc;
    }

    public void setEmc(String emc) {
        this.emc = emc;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCPUQty() {
        return cpuqty;
    }

    public void setCPUQty(String cpuqty) {
        this.cpuqty = cpuqty;
    }

    public String getDataWidth() {
        return dataWidth;
    }

    public void setDataWidth(String dataWidth) {
        this.dataWidth = dataWidth;
    }

    public String getCPUSpeed() {
        return cpuspeed;
    }

    public void setCPUSpeed(String cPUSpeed) {
        this.cpuspeed = cpuspeed;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getAuditHDDSerial() {
        return auditHDDSerial;
    }

    public void setAuditHDDSerial(String auditHDDSerial) {
        this.auditHDDSerial = auditHDDSerial;
    }

    public String getAuditHDDModel() {
        return auditHDDModel;
    }

    public void setAuditHDDModel(String auditHDDModel) {
        this.auditHDDModel = auditHDDModel;
    }

    public String getAuditHDDSize() {
        return auditHDDSize;
    }

    public void setAuditHDDSize(String auditHDDSize) {
        this.auditHDDSize = auditHDDSize;
    }

    public String getAuditHDDStatus() {
        return auditHDDStatus;
    }

    public void setAuditHDDStatus(String auditHDDStatus) {
        this.auditHDDStatus = auditHDDStatus;
    }

    public String getVideoAdapter() {
        return videoAdapter;
    }

    public void setVideoAdapter(String videoAdapter) {
        this.videoAdapter = videoAdapter;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }

    public String getScreenCondition() {
        return screenCondition;
    }

    public void setScreenCondition(String screenCondition) {
        this.screenCondition = screenCondition;
    }

    public String getPlasticCondition() {
        return plasticCondition;
    }

    public void setPlasticCondition(String plasticCondition) {
        this.plasticCondition = plasticCondition;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getRAMSlots() {
        return ramslots;
    }

    public void setRAMSlots(String ramslots) {
        this.ramslots = ramslots;
    }

    public String getRAMPerSlot() {
        return ramperSlot;
    }

    public void setRAMPerSlot(String ramperSlot) {
        this.ramperSlot = ramperSlot;
    }

    public String getRAMParts() {
        return ramparts;
    }

    public void setRAMParts(String ramparts) {
        this.ramparts = ramparts;
    }

    public String getRAMModel() {
        return rammodel;
    }

    public void setRAMModel(String rAMModel) {
        this.rammodel = rammodel;
    }

    public String getTypeofRAM() {
        return typeofRAM;
    }

    public void setTypeofRAM(String typeofRAM) {
        this.typeofRAM = typeofRAM;
    }

    public String getCapacitor() {
        return capacitor;
    }

    public void setCapacitor(String capacitor) {
        this.capacitor = capacitor;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getBatteryHealth() {
        return batteryHealth;
    }

    public void setBatteryHealth(String batteryHealth) {
        this.batteryHealth = batteryHealth;
    }

    public String getAcAdaptor() {
        return acAdaptor;
    }

    public void setAcAdaptor(String acAdaptor) {
        this.acAdaptor = acAdaptor;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public String getDVDDrive() {
        return dvddrive;
    }

    public void setDVDDrive(String dvddrive) {
        this.dvddrive = dvddrive;
    }

    public String getCOAVersion() {
        return coaversion;
    }

    public void setCOAVersion(String coaversion) {
        this.coaversion = coaversion;
    }

    public String getCOAEdition() {
        return coaedition;
    }

    public void setCOAEdition(String cOAEdition) {
        this.coaedition = coaedition;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getLotID() {
        return lotID;
    }

    public void setLotID(String lotID) {
        this.lotID = lotID;
    }

    public String getCustomerAsset() {
        return customerAsset;
    }

    public void setCustomerAsset(String customerAsset) {
        this.customerAsset = customerAsset;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPalette() {
        return palette;
    }

    public void setPalette(String palette) {
        this.palette = palette;
    }

    public String getSoldDate() {
        String dateStr = soldDate;
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US);
        try {
            Date date = df.parse(dateStr);
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public String getSoldHDDSerial() {
        return soldHDDSerial;
    }

    public void setSoldHDDSerial(String soldHDDSerial) {
        this.soldHDDSerial = soldHDDSerial;
    }

    public String getSoldHDDModel() {
        return soldHDDModel;
    }

    public void setSoldHDDModel(String soldHDDModel) {
        this.soldHDDModel = soldHDDModel;
    }

    public String getSoldHDDSize() {
        return soldHDDSize;
    }

    public void setSoldHDDSize(String soldHDDSize) {
        this.soldHDDSize = soldHDDSize;
    }

    public String getSoldHDDSmart() {
        return soldHDDSmart;
    }

    public void setSoldHDDSmart(String soldHDDSmart) {
        this.soldHDDSmart = soldHDDSmart;
    }

    public String getBuildOrder() {
        return buildOrder;
    }

    public void setBuildOrder(String buildOrder) {
        this.buildOrder = buildOrder;
    }

    public String getBuildOS() {
        return buildOS;
    }

    public void setBuildOS(String buildOS) {
        this.buildOS = buildOS;
    }

    public String getBuildSKU() {
        return buildSKU;
    }

    public void setBuildSKU(String buildSKU) {
        this.buildSKU = buildSKU;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
