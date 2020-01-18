package com.system.software.solutions.sts.util;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.type.StringType;

public class DataSourceExcelRequest {

    private List<String> columns;
    private List<SortDescriptor> sort;
    private HashMap<String, Object> data;

    private FilterDescriptor filter;

    public DataSourceExcelRequest() {
        columns = new ArrayList<String>();
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

    public Workbook toDataSourceResult(Session session, Class<?> clazz) {
        Criteria criteria = session.createCriteria(clazz);

        filter(criteria, getFilter(), clazz);

        sort(criteria, sortDescriptors());

        List<Object> list = criteria.list();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("DATA");
        sheet.setDefaultColumnWidth(15);
        CellStyle cellStyle = workbook.createCellStyle();
        Row row = null;
        Cell cell = null;

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);

        //header
        row = sheet.createRow(0);
        for (int j = 0; j < columns.size(); j++) {
            cell = row.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(columns.get(j).toString().toUpperCase());
        }

        //data
        List<String> columns = getColumns();
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            row = sheet.createRow((i + 1));
            for (int j = 0; j < columns.size(); j++) {
                cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                try {
                    Method accessor = new PropertyDescriptor(columns.get(j), clazz).getReadMethod();
                    Object val = accessor.invoke(obj);
                    cell.setCellValue(val.toString());
                } catch (Exception ex) {
                    cell.setCellValue("");
                }
            }
        }
        return workbook;
    }

    private List<SortDescriptor> sortDescriptors() {
        List<SortDescriptor> sort = new ArrayList<SortDescriptor>();

        List<SortDescriptor> sorts = getSort();

        return sort;
    }

    /**
     * @return the columns
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<String> columns) {
        this.columns = columns;
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
