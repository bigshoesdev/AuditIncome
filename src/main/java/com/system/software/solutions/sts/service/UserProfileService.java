package com.system.software.solutions.sts.service;

import java.util.List;

import com.system.software.solutions.sts.model.UserProfile;

 

public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();

	UserProfile save(UserProfile role);

	void delete(Integer id);

	UserProfile findByName(String name);
	
}
