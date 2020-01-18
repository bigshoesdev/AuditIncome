package com.system.software.solutions.sts.util;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.engine.spi.TypedValue;

public class CustomCriteria implements Criterion {

    private static final long serialVersionUID = 1L;
    private String propertyName;
    private String value;

    public CustomCriteria(String propertyName, String value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
        if (columns.length != 1) {
            throw new HibernateException("Like may only be used with single-column properties");
        }
        return "to_char(" + columns[0] + ", 'DD/MM/YYYY HH24:MI') like ?";
    }

    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new TypedValue[]{new TypedValue(new org.hibernate.type.StringType(),
            MatchMode.START.toMatchString(value.toLowerCase()), EntityMode.POJO)};
    }

}
