package com.system.software.solutions.sts.service;

import com.system.software.solutions.sts.model.GRID;

public interface GridService {

    public Integer addGrid(GRID grid);

    public GRID getGrid(int userID, String model);
}
