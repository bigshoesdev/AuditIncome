package com.system.software.solutions.sts.util;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StringType;

public class DataSourceGridRequest {

    private int page;
    private int pageSize;
    private int take;
    private int skip;
    private List<SortDescriptor> sort;
    private List<GroupDescriptor> group;
    private List<AggregateDescriptor> aggregate;
    private HashMap<String, Object> data;

    private FilterDescriptor filter;

    public DataSourceGridRequest() {
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<SortDescriptor> getSort() {
        return sort;
    }

    public void setSort(List<SortDescriptor> sort) {
        this.sort = sort;
    }

    public FilterDescriptor getFilter() {
        return filter;
    }

    public void setFilter(FilterDescriptor filter) {
        this.filter = filter;
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
            case "dateProcessed":
                isDateType = true;
                break;
            case "dateProduced":
                isDateType = true;
                break;
            case "auditDate":
                isDateType = true;
                break;
            case "checkInDate":
                isDateType = true;
                break;
            case "boxOpenDate":
                isDateType = true;
                break;
            case "boxCloseDate":
                isDateType = true;
                break;
            case "soldDate":
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
                        switch (field) {
                            case "auditDate":
                                junction.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                                break;
                            case "checkInDate":
                                junction.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                                break;
                            case "boxOpenDate":
                                junction.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                                break;
                            case "boxCloseDate":
                                junction.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                                break;
                            case "soldDate":
                                junction.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                                break;
                            default:
                                junction.add(Restrictions.ge(field, df.format(minDate)));
                        }
                    } catch (ParseException ex) {
                        System.out.println("Parse Error");
                    }
                } else {
                    junction.add(Restrictions.ge(field, value));
                }
                break;
            case "gte":
                switch (field) {
                    case "auditDate":
                        junction.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) >= ?", value, StringType.INSTANCE));
                        break;
                    case "checkInDate":
                        junction.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) >= ?", value, StringType.INSTANCE));
                        break;
                    case "boxOpenDate":
                        junction.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) >= ?", value, StringType.INSTANCE));
                        break;
                    case "boxCloseDate":
                        junction.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) >= ?", value, StringType.INSTANCE));
                        break;
                    case "soldDate":
                        junction.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) >= ?", value, StringType.INSTANCE));
                        break;
                    default:
                        junction.add(Restrictions.ge(field, value));
                }
                break;
            case "lt":
                switch (field) {
                    case "auditDate":
                        junction.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) < ?", value, StringType.INSTANCE));
                        break;
                    case "checkInDate":
                        junction.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) < ?", value, StringType.INSTANCE));
                        break;
                    case "boxOpenDate":
                        junction.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) < ?", value, StringType.INSTANCE));
                        break;
                    case "boxCloseDate":
                        junction.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) < ?", value, StringType.INSTANCE));
                        break;
                    case "soldDate":
                        junction.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) < ?", value, StringType.INSTANCE));
                        break;
                    default:
                        junction.add(Restrictions.lt(field, value));
                }
                break;
            case "lte":
                if (isDateType) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date maxDate = df.parse(value.toString());
                        maxDate = new Date(maxDate.getTime() + TimeUnit.DAYS.toMillis(1));
                        switch (field) {
                            case "auditDate":
                                junction.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                                break;
                            case "checkInDate":
                                junction.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                                break;
                            case "boxOpenDate":
                                junction.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                                break;
                            case "boxCloseDate":
                                junction.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                                break;
                            case "soldDate":
                                junction.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                                break;
                            default:
                                junction.add(Restrictions.lt(field, df.format(maxDate)));
                        }
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
            Date maxDate = new Date(minDate.getTime() + TimeUnit.DAYS.toMillis(1));
            Conjunction conju = Restrictions.conjunction();
            switch (field) {
                case "auditDate":
                    conju.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                    conju.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "checkInDate":
                    conju.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                    conju.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "boxOpenDate":
                    conju.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                    conju.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "boxCloseDate":
                    conju.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                    conju.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "soldDate":
                    conju.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) >= ?", df.format(minDate), StringType.INSTANCE));
                    conju.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) < ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                default:
                    conju.add(Restrictions.ge(field, df.format(minDate)));
                    conju.add(Restrictions.lt(field, df.format(maxDate)));
            }
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
            switch (field) {
                case "auditDate":
                    disju.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) < ?", df.format(minDate), StringType.INSTANCE));
                    disju.add(Restrictions.sqlRestriction("cast([Audit Date] as datetime) >= ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "checkInDate":
                    disju.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) < ?", df.format(minDate), StringType.INSTANCE));
                    disju.add(Restrictions.sqlRestriction("cast([Check-In Date] as datetime) >= ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "boxOpenDate":
                    disju.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) < ?", df.format(minDate), StringType.INSTANCE));
                    disju.add(Restrictions.sqlRestriction("cast([Box Open Date] as datetime) >= ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "boxCloseDate":
                    disju.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) < ?", df.format(minDate), StringType.INSTANCE));
                    disju.add(Restrictions.sqlRestriction("cast([Box Close Date] as datetime) >= ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                case "soldDate":
                    disju.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) < ?", df.format(minDate), StringType.INSTANCE));
                    disju.add(Restrictions.sqlRestriction("cast([Sold Date] as datetime) >= ?", df.format(maxDate), StringType.INSTANCE));
                    break;
                default:
                    disju.add(Restrictions.lt(field, df.format(minDate)));
                    disju.add(Restrictions.ge(field, df.format(maxDate)));
            }
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

    private static void sort(Criteria criteria, List<SortDescriptor> sort) {
        if (sort != null && !sort.isEmpty()) {
            for (SortDescriptor entry : sort) {
                String field = entry.getField();
                String dir = entry.getDir();

                if (dir.equals("asc")) {
                    switch (field) {
                        case "dateIn":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_IN as date) asc"));
                            break;
                        case "dateInc":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_INC as date) asc"));
                            break;
                        case "dateSold":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_SOLD as date) asc"));
                            break;
                        case "dateProduced":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_PRODUCED as date) asc"));
                            break;
                        case "dateProcessed":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_PROCESSED as date) asc"));
                            break;
                        case "auditDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Audit Date] as date) asc"));
                            break;
                        case "checkInDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Check-In Date] as date) asc"));
                            break;
                        case "boxOpenDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Box Open Date] as date) asc"));
                            break;
                        case "boxCloseDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Box Close Date] as date) asc"));
                            break;
                        case "soldDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Sold Date] as date) asc"));
                            break;
                        default:
                            criteria.addOrder(Order.asc(field));
                            break;
                    }
                } else if (dir.equals("desc")) {
                    switch (field) {
                        case "dateIn":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_IN as date) desc"));
                            break;
                        case "dateInc":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_INC as date) desc"));
                            break;
                        case "dateSold":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_SOLD as date) desc"));
                            break;
                        case "dateProduced":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_PRODUCED as date) desc"));
                            break;
                        case "dateProcessed":
                            criteria.addOrder(CustomOrder.sqlFormula("cast(DATE_PROCESSED as date) desc"));
                            break;
                        case "auditDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Audit Date] as date) desc"));
                            break;
                        case "checkInDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Check-In Date] as date) desc"));
                            break;
                        case "boxOpenDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Box Open Date] as date) desc"));
                            break;
                        case "boxCloseDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Box Close Date] as date) desc"));
                            break;
                        case "soldDate":
                            criteria.addOrder(CustomOrder.sqlFormula("cast([Sold Date] as date) desc"));
                            break;
                        default:
                            criteria.addOrder(Order.desc(field));
                            break;
                    }
                }
            }
        }
    }

    private static String makeDateGroupVal(Object obj) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        {
            if (obj != null) {
                try {
                    Date date = df.parse(obj.toString());
                    df = new SimpleDateFormat("yyyy-MM-dd");
                    return df.format(date);
                } catch (ParseException ex) {
                    System.out.println("Parse Error");
                    return obj.toString();
                }
            } else {
                return null;
            }
        }
    }

    private List<?> groupBy(List<?> items, List<GroupDescriptor> group, Class<?> clazz, final Session session, List<SimpleExpression> parentRestrictions) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (!items.isEmpty() && group != null && !group.isEmpty()) {
            List<List<SimpleExpression>> restrictions = new ArrayList<List<SimpleExpression>>();

            GroupDescriptor descriptor = group.get(0);
            List<AggregateDescriptor> aggregates = descriptor.getAggregates();

            final String field = descriptor.getField();

            Method accessor = new PropertyDescriptor(field, clazz).getReadMethod();

            Object groupValue = accessor.invoke(items.get(0));

            switch (field) {
                case "dateIn":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "dateInc":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "dateSold":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "dateProduced":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "dateProcessed":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "auditDate":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "checkInDate":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "boxOpenDate":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "boxCloseDate":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
                case "soldDate":
                    groupValue = makeDateGroupVal(groupValue);
                    break;
            }

            if (groupValue == null) {
                groupValue = "";
            }

            List<Object> groupItems = createGroupItem(group.size() > 1, clazz, session, result, aggregates, field, groupValue, parentRestrictions);
            List<SimpleExpression> groupRestriction = new ArrayList<SimpleExpression>(parentRestrictions);
            groupRestriction.add(Restrictions.eq(field, groupValue));
            restrictions.add(groupRestriction);

            for (Object item : items) {
                Object currentValue = accessor.invoke(item);

                switch (field) {
                    case "dateIn":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "dateInc":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "dateSold":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "dateProduced":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "dateProcessed":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "auditDate":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "checkInDate":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "boxOpenDate":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "boxCloseDate":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    case "soldDate":
                        currentValue = makeDateGroupVal(currentValue);
                        break;
                    default:
                }

                if (currentValue == null) {
                    currentValue = "";
                }
                Class<?> type = new PropertyDescriptor(field, clazz).getPropertyType();
                boolean isEqual = false;
                if (type == String.class) {
                    String value1 = groupValue.toString();
                    String value2 = currentValue.toString();

                    if (value1.equalsIgnoreCase(value2)) {
                        isEqual = true;
                    }
                } else {
                    if (groupValue.equals(currentValue)) {
                        isEqual = true;
                    }
                }
                if (!isEqual) {
                    groupValue = currentValue;
                    groupItems = createGroupItem(group.size() > 1, clazz, session, result, aggregates, field, groupValue, parentRestrictions);
                    groupRestriction = new ArrayList<SimpleExpression>(parentRestrictions);
                    groupRestriction.add(Restrictions.eq(field, groupValue));
                    restrictions.add(groupRestriction);
                }
                groupItems.add(item);
            }

            if (group.size() > 1) {
                Integer counter = 0;
                for (Map<String, Object> g : result) {
                    g.put("items", groupBy((List<?>) g.get("items"), group.subList(1, group.size()), clazz, session, restrictions.get(counter++)));
                }
            }
        }

        return result;
    }

    private List<Object> createGroupItem(Boolean hasSubgroups, Class<?> clazz, final Session session, ArrayList<Map<String, Object>> result,
            List<AggregateDescriptor> aggregates,
            final String field,
            Object groupValue,
            List<SimpleExpression> aggregateRestrictions) {

        Map<String, Object> groupItem = new HashMap<String, Object>();
        List<Object> groupItems = new ArrayList<Object>();

        result.add(groupItem);

        groupItem.put("value", groupValue);

        groupItem.put("field", field);
        groupItem.put("hasSubgroups", hasSubgroups);

        if (aggregates != null && !aggregates.isEmpty()) {
            Criteria criteria = session.createCriteria(clazz);

            filter(criteria, getFilter(), clazz); // filter the set by the selected criteria

            SimpleExpression currentRestriction = Restrictions.eq(field, groupValue);

            if (aggregateRestrictions != null && !aggregateRestrictions.isEmpty()) {
                for (SimpleExpression simpleExpression : aggregateRestrictions) {
                    criteria.add(simpleExpression);
                }
            }
            criteria.add(currentRestriction);

            groupItem.put("aggregates", calculateAggregates(criteria, aggregates));
        } else {
            groupItem.put("aggregates", new HashMap<String, Object>());
        }
        groupItem.put("items", groupItems);
        return groupItems;
    }

    @SuppressWarnings({"serial", "unchecked"})
    private static Map<String, Object> calculateAggregates(Criteria criteria, List<AggregateDescriptor> aggregates) {
        return (Map<String, Object>) criteria
                .setProjection(createAggregatesProjection(aggregates))
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] value, String[] aliases) {
                        Map<String, Object> result = new HashMap<String, Object>();

                        for (int i = 0; i < aliases.length; i++) {
                            String alias = aliases[i];
                            Map<String, Object> aggregate;

                            String name = alias.split("_")[0];
                            if (result.containsKey(name)) {
                                ((Map<String, Object>) result.get(name)).put(alias.split("_")[1], value[i]);
                            } else {
                                aggregate = new HashMap<String, Object>();
                                aggregate.put(alias.split("_")[1], value[i]);
                                result.put(name, aggregate);
                            }
                        }

                        return result;
                    }

                    @SuppressWarnings("rawtypes")
                    @Override
                    public List transformList(List collection) {
                        return collection;
                    }
                })
                .list()
                .get(0);
    }

    private static ProjectionList createAggregatesProjection(List<AggregateDescriptor> aggregates) {
        ProjectionList projections = Projections.projectionList();
        for (AggregateDescriptor aggregate : aggregates) {
            String alias = aggregate.getField() + "_" + aggregate.getAggregate();
            if (aggregate.getAggregate().equals("count")) {
                projections.add(Projections.count(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("sum")) {
                projections.add(Projections.sum(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("average")) {
                projections.add(Projections.avg(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("min")) {
                projections.add(Projections.min(aggregate.getField()), alias);
            } else if (aggregate.getAggregate().equals("max")) {
                projections.add(Projections.max(aggregate.getField()), alias);
            }
        }
        return projections;
    }

    private List<?> group(final Criteria criteria, final Session session, final Class<?> clazz) {
        List<?> result = new ArrayList<Object>();
        List<GroupDescriptor> group = getGroup();

        if (group != null && !group.isEmpty()) {
            try {
                result = groupBy(criteria.list(), group, clazz, session, new ArrayList<SimpleExpression>());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (HibernateException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static long total(Criteria criteria) {
        long total = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();

        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);

        return total;
    }

    private static void page(Criteria criteria, int take, int skip) {
        criteria.setMaxResults(take);
        criteria.setFirstResult(skip);
    }

    public DataSourceGridResult toDataSourceResult(Session session, Class<?> clazz) {
        Criteria criteria = session.createCriteria(clazz);

        filter(criteria, getFilter(), clazz);

        long total = total(criteria);

        sort(criteria, sortDescriptors());

        page(criteria, getTake(), getSkip());

        DataSourceGridResult result = new DataSourceGridResult();

        result.setTotal(total);

        System.out.println("Total:" + total);

        List<GroupDescriptor> groups = getGroup();

        if (groups != null && !groups.isEmpty()) {
            result.setData(group(criteria, session, clazz));
        } else {
            result.setData(criteria.list());
        }

        List<AggregateDescriptor> aggregates = getAggregate();
        if (aggregates != null && !aggregates.isEmpty()) {
            result.setAggregates(aggregate(aggregates, getFilter(), session, clazz));
        }

        return result;
    }

    private static Map<String, Object> aggregate(List<AggregateDescriptor> aggregates, FilterDescriptor filters, Session session, Class<?> clazz) {
        Criteria criteria = session.createCriteria(clazz);

        filter(criteria, filters, clazz);

        return calculateAggregates(criteria, aggregates);
    }

    private List<SortDescriptor> sortDescriptors() {
        List<SortDescriptor> sort = new ArrayList<SortDescriptor>();

        List<GroupDescriptor> groups = getGroup();
        List<SortDescriptor> sorts = getSort();

        if (groups != null) {
            sort.addAll(groups);
        }

        if (sorts != null) {
            if (groups != null) {
                for (int i = 0; i < sorts.size(); i++) {
                    boolean isAdd = false;
                    for (int j = 0; j < groups.size(); j++) {
                        if (sorts.get(i).getField().equals(groups.get(j).getField())) {
                            isAdd = true;
                        }
                    }

                    if (!isAdd) {
                        sort.add(sorts.get(i));
                    }
                }
            } else {
                sort.addAll(sorts);
            }
        }
        return sort;
    }

    public List<GroupDescriptor> getGroup() {
        return group;
    }

    public void setGroup(List<GroupDescriptor> group) {
        this.group = group;
    }

    public List<AggregateDescriptor> getAggregate() {
        return aggregate;
    }

    public void setAggregate(List<AggregateDescriptor> aggregate) {
        this.aggregate = aggregate;

    }

    public static class SortDescriptor {

        private String field;
        private String dir;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }

    public static class GroupDescriptor extends SortDescriptor {

        private List<AggregateDescriptor> aggregates;

        public GroupDescriptor() {
            aggregates = new ArrayList<AggregateDescriptor>();
        }

        public List<AggregateDescriptor> getAggregates() {
            return aggregates;
        }
    }

    public static class AggregateDescriptor {

        private String field;
        private String aggregate;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getAggregate() {
            return aggregate;
        }

        public void setAggregate(String aggregate) {
            this.aggregate = aggregate;
        }
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
