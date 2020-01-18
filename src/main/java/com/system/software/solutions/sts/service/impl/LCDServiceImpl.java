package com.system.software.solutions.sts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.software.solutions.sts.dao.LCDDao;
import com.system.software.solutions.sts.model.AbstractEntity;
import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.LCD;
import com.system.software.solutions.sts.model.Main;
import com.system.software.solutions.sts.model.RMA;
import com.system.software.solutions.sts.service.LCDService;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.apache.poi.ss.usermodel.Workbook;

@Service("lcdService")
@Transactional(value = "transactionManagerMSsql")
public class LCDServiceImpl implements LCDService {

    @Autowired
    private LCDDao dao;

    @Override
    public void saveLCD(LCD lcd) {
        System.out.println("entra a save Main+++++");
        dao.save(lcd);
    }

    @Override
    public List<LCD> findAll() {
        List<LCD> list = new ArrayList<LCD>();
        list = dao.findAll();
        return list;
    }

    @Override
    public List<LCD> find(String term, String col, int pageSize, int pageNumber) {
        List<LCD> list = new ArrayList<LCD>();
        list = dao.findAll(term, col, pageSize, pageNumber);
        return list;
    }

    @Override
    public AbstractEntity<LCD> getEntity(String term, String col, String order, int pageSize, int pageNumber) {
        return null;//dao.getEntity(term, col, order, pageSize, pageNumber);
    }

    @Override
    public CustomEntity<LCD> getEntity(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        boolean edit = session.getAttribute("edit") == null ? false : Boolean.parseBoolean(String.valueOf(session.getAttribute("edit")));
        String col = request.getParameter("col");
        String order = request.getParameter("order");

        int pageNumber = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String filter = request.getParameter("filter");

        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap> filters = new ArrayList<HashMap>();
        if (filter != null) {
            try {

                filters
                        = objectMapper.readValue(filter, objectMapper.getTypeFactory().constructCollectionType(List.class, HashMap.class));

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        CustomEntity<LCD> grid = dao.getEntity(filters, col, order, pageSize, pageNumber);
        grid.setEditable(edit);
        return grid;
    }

    @Override
    public DataSourceGridResult getEntity(DataSourceGridRequest request) {
        return dao.getEntity(request, LCD.class);
    }

    @Override
    public DataSourceLineChartResult getEntity(DataSourceLineChartRequest request) {
        return dao.getEntity(request, LCD.class);
    }

    @Override
    public DataSourcePieChartResult getEntity(DataSourcePieChartRequest request) {
        return dao.getEntity(request, LCD.class);
    }

    public Workbook getEntity(DataSourceExcelRequest request) {
        return dao.getEntity(request, LCD.class);
    }

    @Override
    public void update(LCD entity) {
        dao.update(entity);

    }

    public LCD findById(long id) {
        return dao.getLCD(id);
    }

}
