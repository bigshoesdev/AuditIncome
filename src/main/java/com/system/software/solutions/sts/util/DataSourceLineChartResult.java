package com.system.software.solutions.sts.util;

import com.system.software.solutions.sts.model.LineChartItem;
import java.util.List;

public class DataSourceLineChartResult {

    private List<List<LineChartItem>> data;

    public List<List<LineChartItem>> getData() {
        return data;
    }
    
    public void setData(List<List<LineChartItem>> data) {
        this.data = data;
    }

}
