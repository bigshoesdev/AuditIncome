/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.software.solutions.sts.util;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.system.software.solutions.sts.model.LineChartItem;
import com.system.software.solutions.sts.model.PieChartItem;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.type.StringType;

public class DataSourcePieChartRequest {

    private HashMap<String, Object> data;

    private String groupBy;

    private FilterDescriptor filter;

    private String dataType;

    private String startDate;

    private String endDate;

    public DataSourcePieChartRequest() {
        filter = new FilterDescriptor();
        data = new HashMap<String, Object>();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        data.put(key, value);
    }

    public FilterDescriptor getFilter() {
        return filter;
    }

    public void setFilter(FilterDescriptor filter) {
        this.filter = filter;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private static void restrict(Junction junction, FilterDescriptor filter, Class<?> clazz) {
        String operator = filter.getOperator();
        String field = filter.getField();
        Object value = filter.getValue();
        boolean ignoreCase = filter.isIgnoreCase();

        String[] nullables = {"isnull", "isnotnull", "isempty", "isnotempty"};

        if (!Arrays.asList(nullables).contains(operator)) {
            try {
                Class<?> type = new PropertyDescriptor(field, clazz).getPropertyType();
                if (type == double.class || type == Double.class) {
                    value = Double.parseDouble(value.toString());
                } else if (type == float.class || type == Float.class) {
                    value = Float.parseFloat(value.toString());
                } else if (type == long.class || type == Long.class) {
                    value = Long.parseLong(value.toString());
                } else if (type == int.class || type == Integer.class) {
                    value = Integer.parseInt(value.toString());
                } else if (type == short.class || type == Short.class) {
                    value = Short.parseShort(value.toString());
                } else if (type == boolean.class || type == Boolean.class) {
                    value = Boolean.parseBoolean(value.toString());
                } else if (type == Date.class) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    String input = value.toString();
                    value = df.parse(input);
                }
            } catch (IntrospectionException e) {
            } catch (NumberFormatException nfe) {
            } catch (ParseException e) {
            }
        }

        boolean isDateType = false;
        switch (field) {
            case "dateIn":
                isDateType = true;
                break;
            case "dateInc":
                isDateType = true;
                break;
            case "dateSold":
                isDateType = true;
                break;
            case "dateProduced":
                isDateType = true;
                break;
            case "dateProcessed":
                isDateType = true;
                break;
        }
        if (isDateType) {
            if (value != null) {
                String input = value.toString();
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
                    value = df.format(iso8601.parse(input));
                    System.out.println(value);
                } catch (ParseException ex) {
                    System.out.println("Parse Error");
                }
            } else {
                isDateType = false;
            }
        }
        switch (operator) {
            case "eq":
                if (isDateType) {
                    makeEqualForDate(field, value, junction);
                } else {
                    if (value instanceof String) {
                        junction.add(Restrictions.ilike(field, value.toString(), MatchMode.EXACT));
                    } else {
                        junction.add(Restrictions.eq(field, value));
                    }
                }
                break;
            case "neq":
                if (isDateType) {
                    makeNotEqualForDate(field, value, junction);
                } else {
                    if (value instanceof String) {
                        junction.add(Restrictions.not(Restrictions.ilike(field, value.toString(), MatchMode.EXACT)));
                    } else {
                        junction.add(Restrictions.ne(field, value));
                    }
                }
                break;
            case "gt":
                if (isDateType) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");;
                        Date minDate = df.parse(value.toString());
                        minDate = new Date(minDate.getTime() + TimeUnit.DAYS.toMillis(1));
                        junction.add(Restrictions.ge(field, df.format(minDate)));
                    } catch (ParseException ex) {
                        System.out.println("Parse Error");
                    }
                } else {
                    junction.add(Restrictions.ge(field, value));
                }
                break;
            case "gte":
                junction.add(Restrictions.ge(field, value));
                break;
            case "lt":
                junction.add(Restrictions.lt(field, value));
                break;
            case "lte":
                if (isDateType) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date maxDate = df.parse(value.toString());
                        maxDate = new Date(maxDate.getTime() + TimeUnit.DAYS.toMillis(1));
                        junction.add(Restrictions.lt(field, df.format(maxDate)));
                    } catch (ParseException ex) {
                        System.out.println("Parse Error");
                    }
                } else {
                    junction.add(Restrictions.ge(field, value));
                }
                break;
            case "startswith":
                junction.add(getLikeExpression(field, value.toString(), MatchMode.START, ignoreCase));
                break;
            case "endswith":
                junction.add(getLikeExpression(field, value.toString(), MatchMode.END, ignoreCase));
                break;
            case "contains":
                junction.add(getLikeExpression(field, value.toString(), MatchMode.ANYWHERE, ignoreCase));
                break;
            case "doesnotcontain":
                junction.add(Restrictions.not(Restrictions.ilike(field, value.toString(), MatchMode.ANYWHERE)));
                break;
            case "isnull":
                junction.add(Restrictions.isNull(field));
                break;
            case "isnotnull":
                junction.add(Restrictions.isNotNull(field));
                break;
            case "isempty":
                junction.add(Restrictions.eq(field, ""));
                break;
            case "isnotempty":
                junction.add(Restrictions.not(Restrictions.eq(field, "")));
                break;
        }
    }

    private static void makeEqualForDate(String field, Object value, Junction junction) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date minDate;
        try {
            minDate = df.parse(value.toString());
            System.out.println(minDate.toString());
            Date maxDate = new Date(minDate.getTime() + TimeUnit.DAYS.toMillis(1));
            System.out.println(maxDate.toString());
            Conjunction conju = Restrictions.conjunction();
            conju.add(Restrictions.ge(field, df.format(minDate)));
            conju.add(Restrictions.lt(field, df.format(maxDate)));
            junction.add(conju);
        } catch (ParseException ex) {
            System.out.println("Parse Date Error");
        }
    }

    private static void makeNotEqualForDate(String field, Object value, Junction junction) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date minDate;
        try {
            minDate = df.parse(value.toString());
            Date maxDate = new Date(minDate.getTime() + TimeUnit.DAYS.toMillis(1));
            Disjunction disju = Restrictions.disjunction();
            disju.add(Restrictions.lt(field, df.format(minDate)));
            disju.add(Restrictions.ge(field, df.format(maxDate)));
            junction.add(disju);
        } catch (ParseException ex) {
            System.out.println("Parse Date Error");
        }
    }

    private static Criterion getLikeExpression(String field, String value, MatchMode mode, boolean ignoreCase) {
        SimpleExpression expression = Restrictions.like(field, value, mode);

        if (ignoreCase == true) {
            expression = expression.ignoreCase();
        }

        return expression;
    }

    private static void filter(Criteria criteria, FilterDescriptor filter, Class<?> clazz) {
        if (filter != null) {
            List<FilterDescriptor> filters = filter.filters;

            if (!filters.isEmpty()) {
                Junction junction = Restrictions.conjunction();

                if (!filter.getFilters().isEmpty() && filter.getLogic().toString().equals("or")) {
                    junction = Restrictions.disjunction();
                }

                for (FilterDescriptor entry : filters) {
                    if (!entry.getFilters().isEmpty()) {
                        filter(criteria, entry, clazz);
                    } else {
                        restrict(junction, entry, clazz);
                    }
                }

                criteria.add(junction);
            }
        }
    }

    public DataSourcePieChartResult toDataSourceResult(Session session, Class<?> clazz) {
        Criteria criteria = session.createCriteria(clazz);

        filter(criteria, getFilter(), clazz);

        Junction junction = Restrictions.conjunction();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDate;
        String endDate;

        SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        iso8601.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            startDate = df.format(iso8601.parse(getStartDate()));
            endDate = df.format(iso8601.parse(getEndDate()));
            switch (getDataType()) {
                case "auditDate":
                    junction.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) > ?", startDate, StringType.INSTANCE));
                    junction.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) < ?", endDate, StringType.INSTANCE));
                    break;
                case "checkInDate":
                    junction.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) > ?", startDate, StringType.INSTANCE));
                    junction.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) < ?", endDate, StringType.INSTANCE));
                    break;
                case "boxOpenDate":
                    junction.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) > ?", startDate, StringType.INSTANCE));
                    junction.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) < ?", endDate, StringType.INSTANCE));
                    break;
                case "boxCloseDate":
                    junction.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) > ?", startDate, StringType.INSTANCE));
                    junction.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) < ?", endDate, StringType.INSTANCE));
                    break;
                case "soldDate":
                    junction.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) > ?", startDate, StringType.INSTANCE));
                    junction.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) < ?", endDate, StringType.INSTANCE));
                    break;
                default:
                    junction.add(Restrictions.gt(getDataType(), startDate));
                    junction.add(Restrictions.lt(getDataType(), endDate));
            }
            criteria.add(junction);
        } catch (ParseException ex) {
            System.out.println("Parse Error");
        }

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.rowCount());
        projectionList.add(Projections.groupProperty(getGroupBy()));

        criteria.setProjection(projectionList);

        List<Object[]> result = criteria.list();

        List<PieChartItem> data = new ArrayList<PieChartItem>();

        Long sum = 0L;
        for (int i = 0; i < result.size(); i++) {
            Object[] obj = result.get(i);
            Random random = new Random();
            int nextInt = random.nextInt(256 * 256 * 256);

            PieChartItem item = new PieChartItem();
            sum += (Long) obj[0];
            item.setCount((Long) obj[0]);
            if (obj[1] != null) {
                item.setCategory(obj[1].toString());
            } else {
                item.setCategory("null");
            }
            item.setColor(String.format("#%06x", nextInt));

            data.add(item);
        }

        for (int i = 0; i < data.size(); i++) {
            PieChartItem item = data.get(i);
            item.setValue(((double) item.getCount()) / ((double) sum) * 100);
        }

        DataSourcePieChartResult charResult = new DataSourcePieChartResult();

        charResult.setData(data);
        return charResult;
    }

    public static class FilterDescriptor {

        private String logic;
        private List<FilterDescriptor> filters;
        private String field;
        private Object value;
        private String operator;
        private boolean ignoreCase = true;

        public FilterDescriptor() {
            filters = new ArrayList<FilterDescriptor>();
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public List<FilterDescriptor> getFilters() {
            return filters;
        }
    }
}
