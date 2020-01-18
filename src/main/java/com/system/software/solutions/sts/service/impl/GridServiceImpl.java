package com.system.software.solutions.sts.service.impl;

import com.system.software.solutions.sts.dao.GridDao;
import com.system.software.solutions.sts.model.GRID;
import com.system.software.solutions.sts.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gridService")
@Transactional(value = "transactionManagerMSsql")
public class GridServiceImpl implements GridService {

    @Autowired
    private GridDao dao;

    @Override
    public Integer addGrid(GRID grid) {
        return dao.addGrid(grid);
    }

    @Override
    public GRID getGrid(int userID, String model) {
        return dao.getGrid(userID, model);
    }

}
