package com.system.software.solutions.sts.controller;

import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.system.software.solutions.sts.formbean.DailyAuditedFB;
import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.LCD;
import com.system.software.solutions.sts.model.Main;
import com.system.software.solutions.sts.model.RMA;
import com.system.software.solutions.sts.service.LCDService;
import com.system.software.solutions.sts.service.MainService;
import com.system.software.solutions.sts.service.RMAService;
import com.system.software.solutions.sts.service.ReportsService;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/json")
public class MainRestController {

    @Autowired
    private MainService mainService;

    @Autowired
    private LCDService lcdService;

    @Autowired
    private RMAService rmaService;

    @Autowired
    private ReportsService reportsService;

    //Kendo Grid Start
    @RequestMapping(value = "/maingrid", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceGridResult listAllMainGrid(@RequestBody DataSourceGridRequest request) {

        DataSourceGridResult result = mainService.getEntity(request);

        return result;
    }

    @RequestMapping(value = "/lcdgrid", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceGridResult listAllLCDGrid(@RequestBody DataSourceGridRequest request) {

        DataSourceGridResult result = lcdService.getEntity(request);

        return result;
    }

    @RequestMapping(value = "/rmagrid", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceGridResult listAllRMAGrid(@RequestBody DataSourceGridRequest request) {

        DataSourceGridResult result = rmaService.getEntity(request);

        return result;
    }

    //Kendo Grid End
    //Kendo Line Chart Start
    @RequestMapping(value = "/mainline", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceLineChartResult listAllMainLine(@RequestBody DataSourceLineChartRequest request) {
        DataSourceLineChartResult result = mainService.getEntity(request);
        return result;
    }

    @RequestMapping(value = "/lcdline", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceLineChartResult listAllLCDLine(@RequestBody DataSourceLineChartRequest request) {
        DataSourceLineChartResult result = lcdService.getEntity(request);
        return result;
    }

    @RequestMapping(value = "/rmaline", method = RequestMethod.POST)
    public @ResponseBody
    DataSourceLineChartResult listAllRMALine(@RequestBody DataSourceLineChartRequest request) {
        DataSourceLineChartResult result = rmaService.getEntity(request);
        return result;
    }

    //Kendo Line Chart Start
    @RequestMapping(value = "/mainpie", method = RequestMethod.POST)
    public @ResponseBody
    DataSourcePieChartResult listAllMainPie(@RequestBody DataSourcePieChartRequest request) {
        DataSourcePieChartResult result = mainService.getEntity(request);
        return result;
    }

    @RequestMapping(value = "/lcdpie", method = RequestMethod.POST)
    public @ResponseBody
    DataSourcePieChartResult listAllLCDPie(@RequestBody DataSourcePieChartRequest request) {
        DataSourcePieChartResult result = lcdService.getEntity(request);
        return result;
    }

    @RequestMapping(value = "/rmapie", method = RequestMethod.POST)
    public @ResponseBody
    DataSourcePieChartResult listAllRMAPie(@RequestBody DataSourcePieChartRequest request) {
        DataSourcePieChartResult result = rmaService.getEntity(request);
        return result;
    }

    // -------------------Retrieve All
    // Users--------------------------------------------------------
    @RequestMapping(value = "/grid1", method = RequestMethod.GET)
    public ResponseEntity<CustomEntity<Main>> listAllUsers(HttpServletRequest request, HttpServletResponse response) {

        CustomEntity<Main> response_ = mainService.getEntity(request, response);

        return new ResponseEntity<CustomEntity<Main>>(response_, HttpStatus.OK);
    }

    @RequestMapping(value = "/lcd1", method = RequestMethod.GET)
    public ResponseEntity<CustomEntity<LCD>> listAllLCD(HttpServletRequest request, HttpServletResponse response) {

        CustomEntity<LCD> response_ = lcdService.getEntity(request, response);

        return new ResponseEntity<CustomEntity<LCD>>(response_, HttpStatus.OK);
    }

    @RequestMapping(value = "/rma1", method = RequestMethod.GET)
    public ResponseEntity<CustomEntity<RMA>> listAllRMA(HttpServletRequest request, HttpServletResponse response) {

        CustomEntity<RMA> response_ = rmaService.getEntity(request, response);

        return new ResponseEntity<CustomEntity<RMA>>(response_, HttpStatus.OK);
    }

    @RequestMapping(value = "/reports/dailyaudited", method = RequestMethod.GET)
    public ResponseEntity<DailyAuditedFB> loadDailyAudited(HttpServletRequest request, HttpServletResponse response) {

        DailyAuditedFB response_ = reportsService.fetchDailyAudited();

        return new ResponseEntity<DailyAuditedFB>(response_, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Main> listAllUsers(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));

        String box = request.getParameter("box");

        Main main = mainService.findById(id);

        if (main == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Main>(HttpStatus.NOT_FOUND);
        }

        main.setBox(box);

        mainService.update(main);
        return new ResponseEntity<Main>(main, HttpStatus.OK);
    }

    @RequestMapping(value = "/lcdupdate", method = RequestMethod.POST)
    public ResponseEntity<LCD> lcdUpdate(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));

        String box = request.getParameter("box");

        LCD main = lcdService.findById(id);

        if (main == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<LCD>(HttpStatus.NOT_FOUND);
        }

        main.setBox(box);

        lcdService.update(main);
        return new ResponseEntity<LCD>(main, HttpStatus.OK);
    }

    @RequestMapping(value = "/rmaupdate", method = RequestMethod.POST)
    public ResponseEntity<RMA> rmaUpdate(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));

        String box = request.getParameter("box");

        RMA main = rmaService.findById(id);

        if (main == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<RMA>(HttpStatus.NOT_FOUND);
        }

        // main.setBox(box);
        rmaService.update(main);
        return new ResponseEntity<RMA>(main, HttpStatus.OK);
    }

}
