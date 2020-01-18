package com.system.software.solutions.sts.service;

import com.system.software.solutions.sts.model.Dashboard;

public interface DashboardService {

    public Integer addDashboard(Dashboard dashboard);
    
    public Boolean deleteDashboard(Integer id);
    
   public Boolean updateDashboardTitle(Integer id, String text);
}
