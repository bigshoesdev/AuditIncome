package com.system.software.solutions.sts.dao;

import java.util.HashMap;
import java.util.List;

import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.RMA;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface RMADao {

    void save(RMA rma);

    public RMA getRMA(long id);

    public void update(RMA entity);

    public List<RMA> findAll(String term, String col, int pageSize, int pageNumber);

    List<RMA> findAll();

    CustomEntity<RMA> getEntity(String term, String col, String order, int pageSize, int pageNumber);

    CustomEntity<RMA> getEntity(List<HashMap> filters, String col, String order, int pageSize, int pageNumber);

    DataSourceGridResult getEntity(DataSourceGridRequest request, Class<?> clazz);

    DataSourceLineChartResult getEntity(DataSourceLineChartRequest request, Class<?> clazz);

    DataSourcePieChartResult getEntity(DataSourcePieChartRequest request, Class<?> clazz);

    Workbook getEntity(DataSourceExcelRequest request, Class<?> clazz);
}
