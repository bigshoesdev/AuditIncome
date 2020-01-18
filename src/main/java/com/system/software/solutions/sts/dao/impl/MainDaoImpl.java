package com.system.software.solutions.sts.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.system.software.solutions.sts.dao.MainDao;
import com.system.software.solutions.sts.model.CustomEntity;
import com.system.software.solutions.sts.model.Main;
import com.system.software.solutions.sts.util.DataSourceExcelRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartRequest;
import com.system.software.solutions.sts.util.DataSourceLineChartResult;
import com.system.software.solutions.sts.util.DataSourceGridRequest;
import com.system.software.solutions.sts.util.DataSourceGridResult;
import com.system.software.solutions.sts.util.DataSourcePieChartRequest;
import com.system.software.solutions.sts.util.DataSourcePieChartResult;
import org.apache.poi.ss.usermodel.Workbook;

@Repository("mainDao")
public class MainDaoImpl implements MainDao {

    static final Logger logger = LoggerFactory.getLogger(MainDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactoryMSSql;

    @Override
    public void save(Main main) {

        Session session = sessionFactoryMSSql.getCurrentSession();
        session.persist(main);

    }

    public Main getMain(long id) {
        Session session = sessionFactoryMSSql.getCurrentSession();

        return (Main) session.get(Main.class, id);
    }

    @Override
    public void update(Main entity) {
        Session session = sessionFactoryMSSql.getCurrentSession();
        session.update(entity);
    }

    @Override
    public List<Main> findAll() {
        Session session = sessionFactoryMSSql.getCurrentSession();

        List<Main> users = (List<Main>) session.createQuery("from Main").list();

        return users;
    }

    // http://www.mkyong.com/hibernate/hibernate-criteria-examples/
    @Override
    public List<Main> findAll(String term, String col, int pageSize, int pageNumber) {

        Session session = sessionFactoryMSSql.getCurrentSession();

        Criteria criteria = session.createCriteria(Main.class);

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

        List<Main> users = (List<Main>) criteria.list();

        return users;
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

    @Override
    public CustomEntity<Main> getEntity(String term, String col, String order, int pageSize, int pageNumber) {
        CustomEntity<Main> entity = new CustomEntity<Main>();

        Session session = sessionFactoryMSSql.getCurrentSession();

        Criteria criteria = session.createCriteria(Main.class);

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

        List<Main> list = (List<Main>) criteria.list();
        entity.setList(list);
        entity.setTotal(resultCount);

        // entity.s
        return entity;
    }

    @Override
    public CustomEntity<Main> getEntity(List<HashMap> filters, String col, String order, int pageSize, int pageNumber) {
        CustomEntity<Main> entity = new CustomEntity<Main>();

        Session session = sessionFactoryMSSql.getCurrentSession();

        Criteria criteria = session.createCriteria(Main.class);

        for (HashMap filter : filters) {
            System.out.println(filter.get("col").toString() + "%" + filter.get("term").toString() + "%");
            criteria.add(Restrictions.like(filter.get("col").toString(), "%" + filter.get("term").toString() + "%"));
        }

        criteria.setProjection(Projections.rowCount());

        Long resultCount = (Long) (criteria.uniqueResult());
        System.out.println("ResultCount" + resultCount);

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

        if (col != null && order != null) {
            if (order.endsWith("asc")) {
                criteria.addOrder(Order.asc(col));
            } else {
                criteria.addOrder(Order.desc(col));
            }
        } else {
            criteria.addOrder(Order.desc("id"));
        }

        List<Main> list = (List<Main>) criteria.list();

        entity.setList(list);
        entity.setTotal(resultCount);

        return entity;
    }

    @Override
    public CustomEntity<Main> fetchPCAudited(int previousDay) {
        CustomEntity<Main> entity = new CustomEntity<Main>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, +1);
        String endDate = simpleDateFormat.format(calendar.getTime());
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -previousDay - 1);
        String startDate = simpleDateFormat.format(calendar.getTime());

        Session session = sessionFactoryMSSql.getCurrentSession();

        SQLQuery query = session.createSQLQuery(
                "SELECT COUNT(*), MIN(DATE_IN) as mindate FROM dbo.MAIN WHERE DATE_IN  IS NOT  NULL and FORM_FACTOR in    ('Tower','Desktop' ,'Low Profile Desktop','Small Form Factor','Ultra Small Form Factor','All-in-one') AND DATE_IN BETWEEN '" + startDate + "' and '" + endDate + "'  GROUP BY YEAR(DATE_IN), MONTH(DATE_IN), DAY(DATE_IN) ORDER BY mindate desc;");

        entity.setList(query.list());

        return entity;
    }

    @Override
    public CustomEntity<Main> fetchLTAudited(int previousDay) {
        CustomEntity<Main> entity = new CustomEntity<Main>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, +1);
        String endDate = simpleDateFormat.format(calendar.getTime());
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -previousDay - 1);
        String startDate = simpleDateFormat.format(calendar.getTime());

        Session session = sessionFactoryMSSql.getCurrentSession();

        SQLQuery query = session.createSQLQuery(
                "SELECT COUNT(*), MIN(DATE_IN) as mindate FROM dbo.MAIN WHERE DATE_IN  IS NOT  NULL and FORM_FACTOR in    ('Laptop', 'Portable', 'Notebook') AND DATE_IN BETWEEN '" + startDate + "' and '" + endDate + "' GROUP BY YEAR(DATE_IN), MONTH(DATE_IN), DAY(DATE_IN) ORDER BY mindate desc;");

        entity.setList(query.list());

        return entity;
    }

}
