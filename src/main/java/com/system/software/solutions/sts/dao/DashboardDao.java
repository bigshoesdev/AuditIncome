package com.system.software.solutions.sts.dao;

import com.system.software.solutions.sts.model.Dashboard;

public interface DashboardDao {

    public Integer addDashboard(Dashboard dashboard);

    public Boolean deleteDashboard(Integer id);

    public Boolean updateDashboardTitle(Integer id, String text);

}
