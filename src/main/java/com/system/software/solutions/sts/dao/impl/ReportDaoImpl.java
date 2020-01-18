package com.system.software.solutions.sts.dao.impl;

import com.system.software.solutions.sts.dao.ReportDao;
import com.system.software.solutions.sts.util.DataSourceReportRequest;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("reportDao")
public class ReportDaoImpl implements ReportDao {

    static final Logger logger = LoggerFactory.getLogger(ReportDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactoryMSSql;

    @Override
    public Workbook getEntity(DataSourceReportRequest request) {
        return request.toDataSourceResult(sessionFactoryMSSql.getCurrentSession());
    }

}
