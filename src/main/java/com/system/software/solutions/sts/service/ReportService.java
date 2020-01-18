package com.system.software.solutions.sts.service;

import com.system.software.solutions.sts.util.DataSourceReportRequest;
import org.apache.poi.ss.usermodel.Workbook;

public interface ReportService {

    Workbook getEntity(DataSourceReportRequest request);
}
