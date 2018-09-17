package com.zy.services;

import java.util.Map;

import com.zy.dao.UserDao;
import com.zy.entity.User;

public class UserServices {
	private UserDao userdao = new UserDao();
	
	public String check_reguser(User user) {
		return userdao.check_reguser(user);
	}
	
	public int register(User user) {
		return userdao.register(user);
	}
	
/*	public String register(User user, String checkcode, String reg_checkcode) {
		return userdao.register(user, checkcode, reg_checkcode);
	}*/
	
	
	public String check_loguser(String name, String pwd) {
		return userdao.check_loguser(name, pwd);
	}
	
	public User login(String name, String pwd) {
		return userdao.login(name, pwd);
	}
	
/*	public Map<String, String> login(String name, String pwd, String login_checkcode, String checkcode) {
		return userdao.login(name, pwd, login_checkcode, checkcode);
	}*/
	
	public User getByID(String id) {
		return userdao.getByID(id);
	}

}
