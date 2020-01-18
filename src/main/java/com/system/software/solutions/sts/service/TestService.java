package com.system.software.solutions.sts.service;

import java.util.List;

import com.system.software.solutions.sts.model.Test;
 

public interface TestService {
 
	void saveTest(Test test);
	List<Test> findAll();
	List<Test> find(String term,String col,int pageSize,int pageNumber) ;
	
}
