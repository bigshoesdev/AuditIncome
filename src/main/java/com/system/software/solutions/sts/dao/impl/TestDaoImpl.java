package com.system.software.solutions.sts.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.system.software.solutions.sts.dao.AbstractDao;
import com.system.software.solutions.sts.dao.TestDao;
import com.system.software.solutions.sts.dao.UserDao;
import com.system.software.solutions.sts.model.Test;
import com.system.software.solutions.sts.model.User;

@Repository("testDao")
public class TestDaoImpl implements TestDao {

    static final Logger logger = LoggerFactory.getLogger(TestDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactoryMSSql;

    @Override
    public void save(Test test) {

        Session session = sessionFactoryMSSql.getCurrentSession();
        session.persist(test);

    }

    @Override
    public List<Test> findAllTest() {
        Session session = sessionFactoryMSSql.getCurrentSession();

        List<Test> users = (List<Test>) session.createQuery("from Test").list();

		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
         for(User user : users){
         Hibernate.initialize(user.getUserProfiles());
         }*/
        return users;
    }

	//http://www.mkyong.com/hibernate/hibernate-criteria-examples/
    @Override
    public List<Test> findAllTest(String term, String col, int pageSize, int pageNumber) {

        Session session = sessionFactoryMSSql.getCurrentSession();

        Criteria criteria = session.createCriteria(Test.class);

        if (col != null && !col.isEmpty()) {
            criteria.add(Restrictions.like(col, "%" + term + "%"));
        }

        criteria.setProjection(Projections.rowCount());

        Long resultCount = (Long) criteria.uniqueResult();
        int qtdPaginas = (resultCount.intValue() / pageSize) + 1;

        criteria.setProjection(null);// reseta a criteria sem a projeção
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.

        if (pageNumber > qtdPaginas) {
            pageNumber = qtdPaginas;
        }
        pageNumber = pageNumber - 1;

        criteria.setFirstResult(pageNumber * pageSize);
        criteria.setMaxResults(pageSize);

        List<Test> users = (List<Test>) criteria.list();

        return users;
    }

}
