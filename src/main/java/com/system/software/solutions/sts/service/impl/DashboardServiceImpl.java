package com.system.software.solutions.sts.service.impl;

import com.system.software.solutions.sts.dao.DashboardDao;
import com.system.software.solutions.sts.model.Dashboard;
import com.system.software.solutions.sts.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dashboardService")
@Transactional(value = "transactionManagerMSsql")
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDao dao;

    @Override
    public Integer addDashboard(Dashboard dashboard) {
        return dao.addDashboard(dashboard);
    }

    @Override
    public Boolean deleteDashboard(Integer id) {
        return dao.deleteDashboard(id);
    }

    @Override
    public Boolean updateDashboardTitle(Integer id, String text) {
        return dao.updateDashboardTitle(id, text);
    }

}
