package com.system.software.solutions.sts.dao;

import com.system.software.solutions.sts.model.GRID;
import java.util.List;


public interface GridDao {

    public Integer addGrid(GRID grid);

    public GRID getGrid(int userID, String model);
}
