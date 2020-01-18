package com.system.software.solutions.sts.util;

import com.system.software.solutions.sts.model.PieChartItem;
import java.util.List;
import org.hibernate.Session;

public class DataSourcePieChartResult {

    private List<PieChartItem> data;

    public List<PieChartItem> getData() {
        return data;
    }

    public void setData(List<PieChartItem> data) {
        this.data = data;
    }

}
