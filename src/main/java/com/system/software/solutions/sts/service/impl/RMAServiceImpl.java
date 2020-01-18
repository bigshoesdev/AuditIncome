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
import com.system.software.solutions.sts.dao.RMADao;
import com.system.software.solutions.sts.model.AbstractEntity;
import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.Main;
import com.system.software.solutions.sts.model.RMA;
import com.system.software.solutions.sts.service.RMAService;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.apache.poi.ss.usermodel.Workbook;

@Service("rmaService")
@Transactional(value = "transactionManagerMSsql")
public class RMAServiceImpl implements RMAService {

    @Autowired
    private RMADao dao;

    @Override
    public void saveRMA(RMA rma) {
        System.out.println("entra a save Main+++++");
        dao.save(rma);
    }

    @Override
    public List<RMA> findAll() {
        List<RMA> list = new ArrayList<RMA>();
        list = dao.findAll();
        return list;
    }

    @Override
    public List<RMA> find(String term, String col, int pageSize, int pageNumber) {
        List<RMA> list = new ArrayList<RMA>();
        list = dao.findAll(term, col, pageSize, pageNumber);
        return list;
    }

    @Override
    public DataSourceGridResult getEntity(DataSourceGridRequest request) {
        return dao.getEntity(request, RMA.class);
    }

    @Override
    public AbstractEntity<RMA> getEntity(String term, String col, String order, int pageSize, int pageNumber) {
        return null;//dao.getEntity(term, col, order, pageSize, pageNumber);
    }

    @Override
    public DataSourceLineChartResult getEntity(DataSourceLineChartRequest request) {
        return dao.getEntity(request, RMA.class);
    }

    @Override
    public DataSourcePieChartResult getEntity(DataSourcePieChartRequest request) {
        return dao.getEntity(request, RMA.class);
    }

    public Workbook getEntity(DataSourceExcelRequest request) {
        return dao.getEntity(request, RMA.class);
    }

    @Override
    public CustomEntity<RMA> getEntity(HttpServletRequest request, HttpServletResponse response) {

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
                filters = objectMapper.readValue(filter, objectMapper.getTypeFactory().constructCollectionType(List.class, HashMap.class));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        CustomEntity<RMA> grid = dao.getEntity(filters, col, order, pageSize, pageNumber);
        grid.setEditable(edit);
        return grid;
    }

    @Override
    public void update(RMA entity) {
        dao.update(entity);

    }

    public RMA findById(long id) {
        return dao.getRMA(id);
    }

}
