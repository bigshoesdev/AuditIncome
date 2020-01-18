package com.system.software.solutions.sts.model;

import java.util.Date;

public class LineChartItem {

    private Date date;
    private Long value;

    public LineChartItem() {}
    
    public LineChartItem(Date date, Long value) {
        setDate(date);
        setValue(value);
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the value
     */
    public Long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Long value) {
        this.value = value;
    }
}
