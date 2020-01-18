package com.system.software.solutions.sts.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.software.solutions.sts.dao.TestDao;
import com.system.software.solutions.sts.dao.UserDao;
import com.system.software.solutions.sts.model.Test;
import com.system.software.solutions.sts.service.TestService;

@Service("testService")
@Transactional(value = "transactionManagerMSsql")
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao dao;

    @Override
    public void saveTest(Test test) {
        System.out.println("entra a save test+++++");
        dao.save(test);
    }

    @Override
    public List<Test> findAll() {
        List<Test> list = new ArrayList<Test>();
        list = dao.findAllTest();
        return list;
    }

    @Override
    public List<Test> find(String term, String col, int pageSize, int pageNumber) {
        List<Test> list = new ArrayList<Test>();
        list = dao.findAllTest(term, col, pageSize, pageNumber);
        return list;
    }

}
