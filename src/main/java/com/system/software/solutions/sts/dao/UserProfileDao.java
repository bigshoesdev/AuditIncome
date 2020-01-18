package com.system.software.solutions.sts.dao;

import java.util.List;

import com.system.software.solutions.sts.model.UserProfile;

public interface UserProfileDao {

    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);

    UserProfile save(UserProfile role);

    void delete(Integer id);

    UserProfile findByName(String name);
}
