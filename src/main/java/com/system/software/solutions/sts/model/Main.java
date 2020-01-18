package com.system.software.solutions.sts.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Main")
public class Main implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERIAL")
    private String serial;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "FORM_FACTOR")
    private String form_factor;

    @Column(name = "CPU")
    private String cpu;

    @Column(name = "CPUSPD")
    private String cpuspd;

    @Column(name = "CPU_CORES")
    private String cpu_cores;

    @Column(name = "CPU_DATA_WIDTH")
    private String cpu_data_width;

    @Column(name = "HDD_SIZE")
    private String hdd_size;

    @Column(name = "HDD_SMART")
    private String hdd_smart;

    @Column(name = "HDD_MODEL")
    private String hdd_model;

    @Column(name = "HDD_SERIAL")
    private String hdd_serial;

    @Column(name = "TOTAL_RAM")
    private String total_ram;

    @Column(name = "RAM_SLOTS")
    private String ram_slots;

    @Column(name = "RAM_PER_SLOT")
    private String ram_per_slot;

    @Column(name = "CAPS")
    private String caps;

    @Column(name = "POWER_ADAPTER")
    private String power_adapter;

    @Column(name = "WEBCAM")
    private String webcam;

    @Column(name = "BATTERY")
    private String battery;

    @Column(name = "SCREEN_CONDITION")
    private String screen_condition;

    @Column(name = "PLASTIC_CONDITION")
    private String plastic_condition;

    @Column(name = "COA_VERSION")
    private String coa_version;

    @Column(name = "COA_EDITION")
    private String coa_edition;

    @Column(name = "INT_SERIAL")
    private String int_serial;

    @Column(name = "CUSTOMER_ID")
    private String customer_id;

    @Column(name = "CUSTOMER_ASSET")
    private String customer_asset;

    @Column(name = "LOT_ID")
    private String lot_id;

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
    @Column(name = "DATE_IN")
    private String dateIn; // tell me what the type of this in db.. my side it is
    // datetime.. can you check in your db. plz

    @Column(name = "KILLDISK")
    private String killdisk;

    @Column(name = "AVAILABLE")
    private String available;

    @Column(name = "PXE_TECH")
    private String pxeTech;

    @Column(name = "INC_TECH")
    private String incTech;

    @Column(name = "HDD_INC_SERIAL")
    private String hddIncSerial;

    @Column(name = "DATE_INC")
    private String dateInc;

    @Column(name = "DATE_SOLD")
    private String dateSold;

    @Column(name = "SCREEN_SIZE")
    private String screenSize;

    @Column(name = "STAND")
    private String stand;

    @Column(name = "EMC")
    private String emc;

    @Column(name = "YEAR")
    private String year;

    @Column(name = "SCRAP")
    private Boolean scrap;

    @Column(name = "INC_HDD_SIZE")
    private String incHddSize;

    @Column(name = "INC_HDD_MODEL")
    private String incHddModel;

    @Transient
    private boolean editable = true;

    public String getPxeTech() {
        return pxeTech;
    }

    public void setPxeTech(String pxeTech) {
        this.pxeTech = pxeTech;
    }

    public String getIncTech() {
        return incTech;
    }

    public void setIncTech(String incTech) {
        this.incTech = incTech;
    }

    public String getHddIncSerial() {
        return hddIncSerial;
    }

    public void setHddIncSerial(String hddIncSerial) {
        this.hddIncSerial = hddIncSerial;
    }

    public String getDateInc() {
        return dateInc;
    }

    public void setDateInc(String dateInc) {
        this.dateInc = dateInc;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getForm_factor() {
        return form_factor;
    }

    public void setForm_factor(String form_factor) {
        this.form_factor = form_factor;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCpuspd() {
        return cpuspd;
    }

    public void setCpuspd(String cpuspd) {
        this.cpuspd = cpuspd;
    }

    public String getCpu_cores() {
        return cpu_cores;
    }

    public void setCpu_cores(String cpu_cores) {
        this.cpu_cores = cpu_cores;
    }

    public String getCpu_data_width() {
        return cpu_data_width;
    }

    public void setCpu_data_width(String cpu_data_width) {
        this.cpu_data_width = cpu_data_width;
    }

    public String getHdd_size() {
        return hdd_size;
    }

    public void setHdd_size(String hdd_size) {
        this.hdd_size = hdd_size;
    }

    public String getHdd_smart() {
        return hdd_smart;
    }

    public void setHdd_smart(String hdd_smart) {
        this.hdd_smart = hdd_smart;
    }

    public String getHdd_model() {
        return hdd_model;
    }

    public void setHdd_model(String hdd_model) {
        this.hdd_model = hdd_model;
    }

    public String getHdd_serial() {
        return hdd_serial;
    }

    public void setHdd_serial(String hdd_serial) {
        this.hdd_serial = hdd_serial;
    }

    public String getTotal_ram() {
        return total_ram;
    }

    public void setTotal_ram(String total_ram) {
        this.total_ram = total_ram;
    }

    public String getRam_slots() {
        return ram_slots;
    }

    public void setRam_slots(String ram_slots) {
        this.ram_slots = ram_slots;
    }

    public String getRam_per_slot() {
        return ram_per_slot;
    }

    public void setRam_per_slot(String ram_per_slot) {
        this.ram_per_slot = ram_per_slot;
    }

    public String getCaps() {
        return caps;
    }

    public void setCaps(String caps) {
        this.caps = caps;
    }

    public String getPower_adapter() {
        return power_adapter;
    }

    public void setPower_adapter(String power_adapter) {
        this.power_adapter = power_adapter;
    }

    public String getWebcam() {
        return webcam;
    }

    public void setWebcam(String webcam) {
        this.webcam = webcam;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getScreen_condition() {
        return screen_condition;
    }

    public void setScreen_condition(String screen_condition) {
        this.screen_condition = screen_condition;
    }

    public String getPlastic_condition() {
        return plastic_condition;
    }

    public void setPlastic_condition(String plastic_condition) {
        this.plastic_condition = plastic_condition;
    }

    public String getCoa_version() {
        return coa_version;
    }

    public void setCoa_version(String coa_version) {
        this.coa_version = coa_version;
    }

    public String getCoa_edition() {
        return coa_edition;
    }

    public void setCoa_edition(String coa_edition) {
        this.coa_edition = coa_edition;
    }

    public String getInt_serial() {
        return int_serial;
    }

    public void setInt_serial(String int_serial) {
        this.int_serial = int_serial;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_asset() {
        return customer_asset;
    }

    public void setCustomer_asset(String customer_asset) {
        this.customer_asset = customer_asset;
    }

    public String getLot_id() {
        return lot_id;
    }

    public void setLot_id(String lot_id) {
        this.lot_id = lot_id;
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

    public String getKilldisk() {
        return killdisk;
    }

    public void setKilldisk(String killdisk) {
        this.killdisk = killdisk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * @return the dateSold
     */
    public String getDateSold() {
        return dateSold;
    }

    /**
     * @param dateSold the dateSold to set
     */
    public void setDateSold(String dateSold) {
        this.dateSold = dateSold;
    }

    /**
     * @return the screenSize
     */
    public String getScreenSize() {
        return screenSize;
    }

    /**
     * @param screenSize the screenSize to set
     */
    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    /**
     * @return the stand
     */
    public String getStand() {
        return stand;
    }

    /**
     * @param stand the stand to set
     */
    public void setStand(String stand) {
        this.stand = stand;
    }

    /**
     * @return the emc
     */
    public String getEmc() {
        return emc;
    }

    /**
     * @param emc the emc to set
     */
    public void setEmc(String emc) {
        this.emc = emc;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the scarp
     */
    public Boolean getScrap() {
        return scrap;
    }

    /**
     * @param scarp the scarp to set
     */
    public void setScrap(Boolean scrap) {
        this.scrap = scrap;
    }

    /**
     * @return the incHDDSize
     */
    public String getIncHddSize() {
        return incHddSize;
    }

    /**
     * @param incHDDSize the incHDDSize to set
     */
    public void setIncHddSize(String incHDDSize) {
        this.incHddSize = incHDDSize;
    }

    /**
     * @return the incHDDModel
     */
    public String getIncHddModel() {
        return incHddModel;
    }

    /**
     * @param incHDDModel the incHDDModel to set
     */
    public void setIncHddModel(String incHDDModel) {
        this.incHddModel = incHDDModel;
    }

}
