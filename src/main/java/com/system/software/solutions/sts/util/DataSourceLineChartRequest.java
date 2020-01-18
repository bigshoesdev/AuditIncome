package com.system.software.solutions.sts.util;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.system.software.solutions.sts.model.LineChartItem;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

public class DataSourceLineChartRequest {

    private HashMap<String, Object> data;

    private List<FilterDescriptor> filters;

    private String dataType;

    private String startDate;

    private String endDate;

    public DataSourceLineChartRequest() {
        filters = new ArrayList<FilterDescriptor>();
        data = new HashMap<String, Object>();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        data.put(key, value);
    }

    public List<FilterDescriptor> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDescriptor> filters) {
        this.filters = filters;
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
                }
            } catch (IntrospectionException e) {
            } catch (NumberFormatException nfe) {
            }
        }

        switch (operator) {
            case "eq":
                if (value instanceof String) {
                    junction.add(Restrictions.ilike(field, value.toString(), MatchMode.EXACT));
                } else {
                    junction.add(Restrictions.eq(field, value));
                }
                break;
            case "neq":
                if (value instanceof String) {
                    junction.add(Restrictions.not(Restrictions.ilike(field, value.toString(), MatchMode.EXACT)));
                } else {
                    junction.add(Restrictions.ne(field, value));
                }
                break;
            case "gt":
                junction.add(Restrictions.gt(field, value));
                break;
            case "gte":
                junction.add(Restrictions.ge(field, value));
                break;
            case "lt":
                junction.add(Restrictions.lt(field, value));
                break;
            case "lte":
                junction.add(Restrictions.le(field, value));
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

    public DataSourceLineChartResult toDataSourceResult(Session session, Class<?> clazz) {
        List<List<LineChartItem>> list = new ArrayList<List<LineChartItem>>();

        for (FilterDescriptor filterDesc : getFilters()) {
            Criteria criteria = session.createCriteria(clazz);

            filter(criteria, filterDesc, clazz);
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

            String groupColumn = "";
            if (getDataType().equals("dateInc")) {
                groupColumn = "DATE_INC";
            } else if (getDataType().equals("dateIn")) {
                groupColumn = "DATE_IN";
            } else if (getDataType().equals("dateSold")) {
                groupColumn = "DATE_SOLD";
            } else if (getDataType().equals("dateProduced")) {
                groupColumn = "DATE_PRODUCED";
            } else if (getDataType().equals("dateProcessed")) {
                groupColumn = "DATE_PROCESSED";
            } else if (getDataType().equals("auditDate")) {
                groupColumn = "[Audit Date]";
            } else if (getDataType().equals("checkInDate")) {
                groupColumn = "[Check-In Date]";
            } else if (getDataType().equals("boxOpenDate")) {
                groupColumn = "[Box Open Date]";
            } else if (getDataType().equals("boxCloseDate")) {
                groupColumn = "[Box Close Date]";
            } else if (getDataType().equals("soldDate")) {
                groupColumn = "[Sold Date]";
            }

            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.rowCount());
            projectionList.add(Projections.sqlGroupProjection("CAST(" + groupColumn + " AS DATE) as NEW_DATE", "CAST(" + groupColumn + " AS DATE)", new String[]{"NEW_DATE"}, new Type[]{StandardBasicTypes.DATE}));

            criteria.setProjection(projectionList);
            List<Object[]> result = criteria.list();

            List<LineChartItem> seriesAry = new ArrayList();

            for (int i = 0; i < result.size(); i++) {
                Object[] obj = result.get(i);

                LineChartItem item = new LineChartItem();
                item.setValue((Long) obj[0]);
                item.setDate((Date) obj[1]);

                seriesAry.add(item);
            }
            list.add(seriesAry);
        }

        DataSourceLineChartResult charResult = new DataSourceLineChartResult();

        charResult.setData(list);

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
