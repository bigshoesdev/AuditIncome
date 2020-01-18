package com.system.software.solutions.sts.dao;

import java.util.HashMap;
import java.util.List;

import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.LCD;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface LCDDao {

    void save(LCD lcd);

    public LCD getLCD(long id);

    public void update(LCD entity);

    public List<LCD> findAll(String term, String col, int pageSize, int pageNumber);

    List<LCD> findAll();

    CustomEntity<LCD> getEntity(String term, String col, String order, int pageSize, int pageNumber);

    CustomEntity<LCD> getEntity(List<HashMap> filters, String col, String order, int pageSize, int pageNumber);

    DataSourceGridResult getEntity(DataSourceGridRequest request, Class<?> clazz);

    DataSourceLineChartResult getEntity(DataSourceLineChartRequest request, Class<?> clazz);

    DataSourcePieChartResult getEntity(DataSourcePieChartRequest request, Class<?> clazz);

    Workbook getEntity(DataSourceExcelRequest request, Class<?> clazz);

    CustomEntity<LCD> fetchDailyAudited(int previousDays);

}
