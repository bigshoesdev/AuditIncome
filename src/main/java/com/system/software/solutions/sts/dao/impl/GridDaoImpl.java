package com.system.software.solutions.sts.dao.impl;

import com.system.software.solutions.sts.dao.GridDao;
import com.system.software.solutions.sts.model.GRID;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("gridDao")
public class GridDaoImpl implements GridDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer addGrid(GRID grid) {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(GRID.class);
        Junction junction = Restrictions.conjunction();
        junction.add(Restrictions.eq("userID", grid.getUserID()));
        junction.add(Restrictions.eq("model", grid.getModel()));

        criteria.add(junction);
        GRID g = (GRID) criteria.uniqueResult();

        if (g != null) {
            g.setOptions(grid.getOptions());
            return (Integer) session.save(g);
        }

        return (Integer) session.save(grid);
    }

    @Override
    public GRID getGrid(int userID, String model) {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(GRID.class);

        Junction junction = Restrictions.conjunction();
        junction.add(Restrictions.eq("userID", userID));
        junction.add(Restrictions.eq("model", model));
        
        criteria.add(junction);
        GRID g = (GRID) criteria.uniqueResult();
        return g;
    }

}
