package com.system.software.solutions.sts.service.impl;

import com.system.software.solutions.sts.dao.ReportDao;
import com.system.software.solutions.sts.service.ReportService;
import com.system.software.solutions.sts.util.DataSourceReportRequest;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reportService")
@Transactional(value = "transactionManagerMSsql")
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public Workbook getEntity(DataSourceReportRequest request) {
        return reportDao.getEntity(request);
    }

}
