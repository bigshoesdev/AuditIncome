/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.software.solutions.sts.util;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class CustomOrder extends Order{

    private String sqlExpression;

    protected CustomOrder(String sqlExpression) {
        super(sqlExpression, true);
        this.sqlExpression = sqlExpression;
    }

    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return sqlExpression;
    }

    public static Order sqlFormula(String sqlFormula) {
        return new CustomOrder(sqlFormula);
    }

    public String toString() {
        return sqlExpression;
    }
}
