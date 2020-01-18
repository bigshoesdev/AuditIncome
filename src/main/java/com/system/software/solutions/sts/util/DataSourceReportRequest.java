package com.system.software.solutions.sts.util;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.system.software.solutions.sts.model.LCD;
import com.system.software.solutions.sts.model.Main;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

public class DataSourceReportRequest {

    private String customerID;
    private String lotID;

    private HashMap<String, Object> data;

    public DataSourceReportRequest() {
        data = new HashMap<String, Object>();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        data.put(key, value);
    }

    public Workbook toDataSourceResult(Session session) {
        Object customerIDObject = this.getCustomerID();
        Object lotIDObject = this.getLotID();

        Criteria criteriaMain = session.createCriteria(Main.class);

        Junction junction = Restrictions.conjunction();
        junction.add(Restrictions.eq("customer_id", customerIDObject));
        junction.add(Restrictions.eq("lot_id", lotIDObject));
        criteriaMain.add(junction);

        List<Object> listMain =  criteriaMain.list();

        System.out.println(listMain.size());

        Criteria criteriaLcd = session.createCriteria(LCD.class);

        Junction junction1 = Restrictions.conjunction();
        junction1.add(Restrictions.eq("customerId", customerIDObject));
        junction1.add(Restrictions.eq("lotId", lotIDObject));
        criteriaLcd.add(junction1);

        List<Object> listLcd = criteriaLcd.list();

        System.out.println(listLcd.size());

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("DATA");
        sheet.setDefaultColumnWidth(20);
        CellStyle cellStyle = workbook.createCellStyle();
        Row row = null;
        Cell cell = null;

        //Create Header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);

        //header
        row = sheet.createRow(0);
        String columns[] = {"Number", "Serial", "Manufacturer", "Model", "Form Factor", "Customer ID", "Customer Asset", "Lot ID", "Inc HDD Serial", "Check-in Date", "Audit Date"};
        for (int i = 0; i < columns.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(columns[i].toUpperCase());
        }

        Integer number = 1;

        for (int i = 0; i < listMain.size(); i++) {
            Main main = (Main) listMain.get(i);
            row = sheet.createRow(number);

            String colData[] = new String[11];
            colData[0] = number.toString();
            colData[1] = main.getSerial();
            colData[2] = main.getManufacturer();
            colData[3] = main.getModel();
            colData[4] = main.getForm_factor();
            colData[5] = main.getCustomer_id();
            colData[6] = main.getCustomer_asset();
            colData[7] = main.getLot_id();
            colData[8] = main.getHddIncSerial();
            colData[9] = main.getDateInc();
            colData[10] = main.getDateIn();

            for (int j = 0; j < colData.length; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                try {
                    cell.setCellValue(colData[j]);
                } catch (Exception ex) {
                    cell.setCellValue("");
                }
            }

            number++;
        }

        for (int i = 0; i < listLcd.size(); i++) {
            LCD lcd = (LCD) listLcd.get(i);
            row = sheet.createRow(number);

            String colData[] = new String[11];
            colData[0] = number.toString();
            colData[1] = lcd.getSerial();
            colData[2] = lcd.getManufacturer();
            colData[3] = lcd.getModel();
            colData[4] = "";
            colData[5] = lcd.getCustomerId();
            colData[6] = lcd.getCustomerAsset();
            colData[7] = lcd.getLotId();
            colData[8] = "";
            colData[9] = "";
            colData[10] = lcd.getDateIn();

            for (int j = 0; j < colData.length; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                try {
                    cell.setCellValue(colData[j]);
                } catch (Exception ex) {
                    cell.setCellValue("");
                }
            }

            number++;
        }

        return workbook;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getLotID() {
        return lotID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setLotID(String lotID) {
        this.lotID = lotID;
    }
}
