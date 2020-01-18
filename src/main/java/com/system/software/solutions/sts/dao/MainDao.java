package com.system.software.solutions.sts.dao;

import java.util.HashMap;
import java.util.List;

import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.Main;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface MainDao {

    void save(Main Main);

    public Main getMain(long id);

    public void update(Main entity);

    public List<Main> findAll(String term, String col, int pageSize, int pageNumber);

    List<Main> findAll();

    CustomEntity<Main> getEntity(String term, String col, String order, int pageSize, int pageNumber);

    DataSourceGridResult getEntity(DataSourceGridRequest request, Class<?> clazz);

    DataSourceLineChartResult getEntity(DataSourceLineChartRequest request, Class<?> clazz);

    DataSourcePieChartResult getEntity(DataSourcePieChartRequest request, Class<?> clazz);

    Workbook getEntity(DataSourceExcelRequest request, Class<?> clazz);

    CustomEntity<Main> getEntity(List<HashMap> filters, String col, String order, int pageSize, int pageNumber);

    CustomEntity<Main> fetchPCAudited(int previousDays);

    CustomEntity<Main> fetchLTAudited(int pageSize);

}
