package com.system.software.solutions.sts.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.system.software.solutions.sts.model.AbstractEntity;
import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.Main;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface MainService {

    void saveMain(Main Main);

    void update(Main entity);

    Main findById(long id);

    List<Main> findAll();

    List<Main> find(String term, String col, int pageSize, int pageNumber);

    AbstractEntity<Main> getEntity(String term, String col, String order, int pageSize, int pageNumber);

    CustomEntity<Main> getEntity(HttpServletRequest request, HttpServletResponse response);

    DataSourceGridResult getEntity(DataSourceGridRequest request);

    DataSourceLineChartResult getEntity(DataSourceLineChartRequest request);

    DataSourcePieChartResult getEntity(DataSourcePieChartRequest request);

    Workbook getEntity(DataSourceExcelRequest request);

}
