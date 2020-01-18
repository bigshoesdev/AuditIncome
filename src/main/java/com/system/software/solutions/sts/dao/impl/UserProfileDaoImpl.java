package com.system.software.solutions.sts.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.system.software.solutions.sts.dao.AbstractDao;
import com.system.software.solutions.sts.dao.UserProfileDao;
import com.system.software.solutions.sts.model.UserProfile;
 


@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>implements UserProfileDao{

	public UserProfile findById(int id) {
		return getByKey(id);
	}

	public UserProfile findByType(String type) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("name", type));
		return (UserProfile) crit.uniqueResult();
	}
	
	public UserProfile findByName(String name) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("name", name));
		return (UserProfile) crit.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserProfile> findAll(){
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("name"));
		return (List<UserProfile>)crit.list();
	}

	@Override
	public UserProfile save(UserProfile role) {
		if(role.getId()==null)
		{
		persist(role);
		}else
		{
			update(role);
		}
	 
		return role;
	}

	@Override
	public void delete(Integer id) {
		UserProfile role=new UserProfile();
		role.setId(id);
		delete(role);
		
	}
	
}
