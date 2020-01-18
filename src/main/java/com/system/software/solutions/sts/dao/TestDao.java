package com.system.software.solutions.sts.dao;

import java.util.List;

import com.system.software.solutions.sts.model.Test;

public interface TestDao {

    void save(Test test);

    public List<Test> findAllTest(String term, String col, int pageSize, int pageNumber);

    List<Test> findAllTest();

}
