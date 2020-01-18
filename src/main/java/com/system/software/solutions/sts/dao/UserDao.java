package com.system.software.solutions.sts.dao;

import java.util.List;

import com.system.software.solutions.sts.model.User;

public interface UserDao {

    User findById(int id);

    User findBySSO(String sso);

    void save(User user);

    void deleteBySSO(String sso);

    List<User> findAllUsers();

}
