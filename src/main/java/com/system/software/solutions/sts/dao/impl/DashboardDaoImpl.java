package com.system.software.solutions.sts.dao.impl;

import com.system.software.solutions.sts.dao.DashboardDao;
import com.system.software.solutions.sts.model.Dashboard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("dashboardDao")
public class DashboardDaoImpl implements DashboardDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer addDashboard(Dashboard dashboard) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.save(dashboard);
    }

    @Override
    public Boolean deleteDashboard(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Dashboard dash = (Dashboard) session.get(Dashboard.class, id);
        session.delete(dash);
        return true;
    }

    @Override
    public Boolean updateDashboardTitle(Integer id, String text) {
        Session session = sessionFactory.getCurrentSession();
        Dashboard dash = (Dashboard) session.get(Dashboard.class, id);
        dash.setTitle(text);
        session.save(dash);
        return true;
    }

}
