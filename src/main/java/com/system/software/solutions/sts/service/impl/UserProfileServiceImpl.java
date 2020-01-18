package com.system.software.solutions.sts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.software.solutions.sts.dao.UserProfileDao;
import com.system.software.solutions.sts.model.UserProfile;
import com.system.software.solutions.sts.service.UserProfileService;

@Service("userProfileService")
@Transactional(value = "transactionManager")
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileDao dao;

    public UserProfile findById(int id) {
        return dao.findById(id);
    }

    public UserProfile findByType(String type) {
        return dao.findByType(type);
    }

    public UserProfile findByName(String name) {
        return dao.findByName(name);
    }

    public List<UserProfile> findAll() {
        return dao.findAll();
    }

    @Override
    public UserProfile save(UserProfile role) {
        return dao.save(role);

    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);

    }
}
