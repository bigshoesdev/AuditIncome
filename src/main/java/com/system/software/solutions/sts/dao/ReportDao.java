package com.system.software.solutions.sts.dao;

import com.system.software.solutions.sts.util.DataSourceReportRequest;
import org.apache.poi.ss.usermodel.Workbook;

public interface ReportDao {
    Workbook getEntity(DataSourceReportRequest request);
}
