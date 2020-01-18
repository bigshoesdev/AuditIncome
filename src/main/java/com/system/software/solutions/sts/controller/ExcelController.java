package com.system.software.solutions.sts.controller;

import com.system.software.solutions.sts.service.LCDService;
import com.system.software.solutions.sts.service.MainService;
import com.system.software.solutions.sts.service.RMAService;
import com.system.software.solutions.sts.service.ReportService;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceExcelResult;
import com.system.software.solutions.sts.util.DataSourceReportRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private MainService mainService;

    @Autowired
    private LCDService lcdService;

    @Autowired
    private RMAService rmaService;

    @Autowired
    private ReportService reportService;
    
    @Autowired
    private HttpServletRequest servletRequest;

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceExcelResult listAllMainToExcel(@RequestBody DataSourceExcelRequest request) {
        Workbook workbook = mainService.getEntity(request);
        String tmpDir = "/resources/tmp/";
        String tmpPath = servletRequest.getServletContext().getRealPath(tmpDir);
        if (!new File(tmpPath).exists()) {
            new File(tmpPath).mkdir();
        }

        String fileName = getRandomString() + ".xlsx";
        File file = new File(tmpPath + "/" + fileName);

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            System.out.println("File error");
        }

        DataSourceExcelResult result = new DataSourceExcelResult();

        result.setExcelFile(fileName);

        return result;
    }

    @RequestMapping(value = "/lcd", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceExcelResult listAllLcdToExcel(@RequestBody DataSourceExcelRequest request) {
        Workbook workbook = lcdService.getEntity(request);
        String tmpDir = "/resources/tmp/";
        String tmpPath = servletRequest.getServletContext().getRealPath(tmpDir);
        if (!new File(tmpPath).exists()) {
            new File(tmpPath).mkdir();
        }

        String fileName = getRandomString() + ".xlsx";
        File file = new File(tmpPath + "/" + fileName);

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            System.out.println("File error");
        }

        DataSourceExcelResult result = new DataSourceExcelResult();

        result.setExcelFile(fileName);

        return result;
    }

    @RequestMapping(value = "/rma", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceExcelResult listAllRmaToExcel(@RequestBody DataSourceExcelRequest request) {
        Workbook workbook = rmaService.getEntity(request);
        String tmpDir = "/resources/tmp/";
        String tmpPath = servletRequest.getServletContext().getRealPath(tmpDir);
        if (!new File(tmpPath).exists()) {
            new File(tmpPath).mkdir();
        }

        String fileName = getRandomString() + ".xlsx";
        File file = new File(tmpPath + "/" + fileName);

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            System.out.println("File error");
        }

        DataSourceExcelResult result = new DataSourceExcelResult();

        result.setExcelFile(fileName);

        return result;
    }

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceExcelResult reportToExcel(@RequestBody DataSourceReportRequest request) {
        Workbook workbook = reportService.getEntity(request);
        
        String tmpDir = "/resources/tmp/";
        String tmpPath = servletRequest.getServletContext().getRealPath(tmpDir);
        if (!new File(tmpPath).exists()) {
            new File(tmpPath).mkdir();
        }

        String fileName = getRandomString() + ".xlsx";
        File file = new File(tmpPath + "/" + fileName);

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception ex) {
            System.out.println("File error");
        }

        DataSourceExcelResult result = new DataSourceExcelResult();

        result.setExcelFile(fileName);

        return result;
    }

    private String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}
