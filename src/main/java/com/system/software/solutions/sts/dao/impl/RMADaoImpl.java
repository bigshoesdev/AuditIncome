package com.system.software.solutions.sts.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.system.software.solutions.sts.dao.RMADao;
import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.RMA;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.apache.poi.ss.usermodel.Workbook;

@Repository("rmaDao")
public class RMADaoImpl implements RMADao {

    static final Logger logger = LoggerFactory.getLogger(RMADaoImpl.class);
    @Autowired
    private SessionFactory sessionFactoryMSSql;

    @Override
    public void save(RMA rma) {

        Session session = sessionFactoryMSSql.getCurrentSession();
        session.persist(rma);

    }

    public RMA getRMA(long id) {
        Session session = sessionFactoryMSSql.getCurrentSession();

        return (RMA) session.get(RMA.class, id);
    }

    @Override
    public void update(RMA entity) {
        Session session = sessionFactoryMSSql.getCurrentSession();
        session.update(entity);
    }

    @Override
    public List<RMA> findAll() {
        Session session = sessionFactoryMSSql.getCurrentSession();

        List<RMA> users = (List<RMA>) session.createQuery("from RMA").list();

        // No need to fetch userProfiles since we are not showing them on list
        // page. Let them lazy load.
        // Uncomment below lines for eagerly fetching of userProfiles if you
        // want.
		/*
         * for(User user : users){ Hibernate.initialize(user.getUserProfiles());
         * }
         */
        return users;
    }

    // http://www.mkyong.com/hibernate/hibernate-criteria-examples/
    @Override
    public List<RMA> findAll(String term, String col, int pageSize, int pageNumber) {

        Session session = sessionFactoryMSSql.getCurrentSession();

        Criteria criteria = session.createCriteria(RMA.class);

        if (col != null && !col.isEmpty()) {
            criteria.add(Restrictions.like(col, "%" + term + "%"));
        }

        criteria.setProjection(Projections.rowCount());

        Long resultCount = (Long) criteria.uniqueResult();
        int qtdPaginas = (resultCount.intValue() / pageSize) + 1;

        criteria.setProjection(null);// reseta a criteria sem a projeção
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
        // duplicates.

        if (pageNumber > qtdPaginas) {
            pageNumber = qtdPaginas;
        }
        pageNumber = pageNumber - 1;

        criteria.setFirstResult(pageNumber * pageSize);
        criteria.setMaxResults(pageSize);

        List<RMA> users = (List<RMA>) criteria.list();

        return users;
    }

    @Override
    public CustomEntity<RMA> getEntity(String term, String col, String order, int pageSize, int pageNumber) {
        CustomEntity<RMA> entity = new CustomEntity<RMA>();

        Session session = sessionFactoryMSSql.getCurrentSession();

        Criteria criteria = session.createCriteria(RMA.class);

        if (col != null && !col.isEmpty()) {
            criteria.add(Restrictions.like(col, "%" + term + "%"));
        }

        criteria.setProjection(Projections.rowCount());

        Long resultCount = (Long) criteria.uniqueResult();
        int qtdPaginas = (resultCount.intValue() / pageSize) + 1;

        criteria.setProjection(null);// reseta a criteria sem a projeção
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid
        // duplicates.

        if (pageNumber > qtdPaginas) {
            pageNumber = qtdPaginas;
        }
        pageNumber = pageNumber - 1;

        criteria.setFirstResult(pageNumber * pageSize);
        criteria.setMaxResults(pageSize);

        List<RMA> list = (List<RMA>) criteria.list();
        entity.setList(list);
        entity.setTotal(resultCount);

        //	entity.s
        return entity;
    }

    @Override
    public CustomEntity<RMA> getEntity(List<HashMap> filters, String col, String order, int pageSize, int pageNumber) {
        CustomEntity<RMA> entity = new CustomEntity<RMA>();

        Session session = sessionFactoryMSSql.getCurrentSession();

        Criteria criteria = session.createCriteria(RMA.class);

        for (HashMap filter : filters) {
            System.out.println(filter.get("col").toString() + "%" + filter.get("term").toString() + "%");
            criteria.add(Restrictions.like(filter.get("col").toString(), "%" + filter.get("term").toString() + "%"));
        }

        criteria.setProjection(Projections.rowCount());

        Long resultCount = (Long) criteria.uniqueResult();
        resultCount = resultCount != null ? resultCount.intValue() : 0L;
        int qtdPaginas = (resultCount.intValue() / pageSize) + 1;

        criteria.setProjection(null);// reseta a criteria sem a projeção
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);// To avoid  // duplicates.

        if (pageNumber > qtdPaginas) {
            pageNumber = qtdPaginas;
        }
        pageNumber = pageNumber - 1;

        criteria.setFirstResult(pageNumber * pageSize);
        criteria.setMaxResults(pageSize);

        if (col != null && order != null) {
            if (order.endsWith("asc")) {
                criteria.addOrder(Order.asc(col));
            } else {
                criteria.addOrder(Order.desc(col));
            }
        }

        List<RMA> list = (List<RMA>) criteria.list();

        entity.setList(list);
        entity.setTotal(resultCount);

        return entity;
    }

    @Override
    public DataSourceGridResult getEntity(DataSourceGridRequest request, Class<?> clazz) {
        return request.toDataSourceResult(sessionFactoryMSSql.getCurrentSession(), clazz);
    }

    @Override
    public DataSourceLineChartResult getEntity(DataSourceLineChartRequest request, Class<?> clazz) {
        return request.toDataSourceResult(sessionFactoryMSSql.getCurrentSession(), clazz);
    }

    @Override
    public DataSourcePieChartResult getEntity(DataSourcePieChartRequest request, Class<?> clazz) {
        return request.toDataSourceResult(sessionFactoryMSSql.getCurrentSession(), clazz);
    }

    @Override
    public Workbook getEntity(DataSourceExcelRequest request, Class<?> clazz) {
        return request.toDataSourceResult(sessionFactoryMSSql.getCurrentSession(), clazz);
    }

}
