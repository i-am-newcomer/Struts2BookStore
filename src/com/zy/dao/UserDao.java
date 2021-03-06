package com.zy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.zy.db.DBConnect;
import com.zy.entity.User;

public class UserDao extends DBConnect {
	
	private ResultSet rs;
	
	//注册验证方式一：采用struts2的验证方式，即validateXxx-validate-xxx
	public String check_reguser(User user) {
		String sql1 = "SELECT * FROM customers WHERE cust_email=? OR cust_phone=? OR cust_name=?";
		String result = null;
		//System.out.println("name: "+user.getName());
		rs = this.executeQuery(sql1, user.getEmail(), user.getPhone(), user.getName());
		try {
			if(rs.next()) {
				if(rs.getString(4).equals(user.getEmail())) {
					result = "该邮箱已被注册";
				}
				else if(rs.getString(5).equals(user.getPhone())) {
					result = "该手机号已被注册";
				}
				else {
					result = "该用户名已被注册";
				}
			}
			else if(!user.getEmail().matches(".+@.+\\.com$")) {
				result = "邮箱格式不正确";
			}
			else if(!user.getPhone().matches("^1[0-9]{10}$")) {
				result = "手机号格式不正确";
			}
			else if(user.getName().length()<4) {
				result = "用户名长度不得少于四位";
			}
			else if(user.getPwd().length()<6) {
				result = "密码长度不得少于6位";
			}
			else if(!user.getPwd().equals(user.getConfpwd())) {
				result = "密码和确认密码必须一致";
			}
			else {
				result = "true";
			}
			this.close();
		}
		catch (SQLException e) {
			result = "服务器异常";
			e.printStackTrace();
		}
		return result;
	}
	
	public int register(User user) {
		System.out.println("user: "+user);
		String sql2 = "INSERT INTO customers(cust_name, cust_password, cust_email, cust_phone) VALUES(?,?,?,?)";
		return this.executeUpdate(sql2, user.getName(), user.getPwd(), user.getEmail(), user.getPhone());
	}
	
	//注册验证方式二，不采用struts2的验证方式
/*	public String register(User user, String checkcode, String reg_checkcode) {
		String sql1 = "SELECT * FROM customers WHERE cust_email=? OR cust_phone=? OR cust_name=?";
		String result = null;
		System.out.println("name: "+user.getName());
		rs = this.executeQuery(sql1, user.getEmail(), user.getPhone(), user.getName());
		try {
			if(rs.next()) {
				if(rs.getString(4).equals(user.getEmail())) {
					result = "该邮箱已被注册";
				}
				else if(rs.getString(5).equals(user.getPhone())) {
					result = "该手机号已被注册";
				}
				else {
					result = "该用户名已被注册";
				}
			}
			else if(!user.getEmail().matches(".+@.+\\.com$")) {
				result = "邮箱格式不正确";
			}
			else if(!user.getPhone().matches("^1[0-9]{10}$")) {
				result = "手机号格式不正确";
			}
			else if(user.getName().length()<4) {
				result = "用户名长度不得少于四位";
			}
			else if(user.getPwd().length()<6) {
				result = "密码长度不得少于6位";
			}
			else if(!user.getPwd().equals(user.getConfpwd())) {
				result = "密码和确认密码必须一致";
			}
			else if(!checkcode.equals(reg_checkcode)) {
				result = "验证码输入错误";
			}
			else {
				result = "true";
				String sql2 = "INSERT INTO customers(cust_name, cust_password, cust_email, cust_phone) VALUES(?,?,?,?)";
				this.executeUpdate(sql2, user.getName(), user.getPwd(), user.getEmail(), user.getPhone());
			}
			this.close();
		}
		catch (SQLException e) {
			result = "服务器异常";
			e.printStackTrace();
		}
		return result;
	}
	*/
	
	//登录验证方式一， 采用struts2的验证机制
	public String check_loguser(String name, String pwd) {
		String sql = "SELECT * FROM customers WHERE cust_name=? OR cust_email=? OR cust_phone=?";
		rs = this.executeQuery(sql, name, name, name);
		String result = null;
		try {
			if(rs.next()) {
				if(rs.getString(3).equals(pwd)) {
					result="true";
				}
				else {
					result = "密码错误";
				}
			}
			else {
				result = "用户名不存在";
			}
			this.close();
		} 
		catch (SQLException e) {
			result = "服务器异常";
			e.printStackTrace();
		}
		return result;	
	}
	
	public User login(String name, String pwd) {
		String sql = "SELECT * FROM customers WHERE cust_name=? OR cust_email=? OR cust_phone=?";
		rs = this.executeQuery(sql, name, name, name);
		try {
			if(rs.next()) {
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	//登录验证方式二，采用自定义的验证方式
/*	public Map<String, String> login(String name, String pwd, String login_checkcode, String checkcode) {
		String sql = "SELECT * FROM customers WHERE cust_name=? OR cust_email=? OR cust_phone=?";
		rs = this.executeQuery(sql, name, name, name);
		String result = null;
		Map<String, String> resultmap = new HashMap<>();
		try {
			if(rs.next()) {
				if(rs.getString(3).equals(pwd)) {
					if(login_checkcode.equals(checkcode))
					{
						result = String.valueOf(rs.getInt(1));
						resultmap.put("successresult", result);
					}
					else {
						result = "验证码输入错误";
						resultmap.put("failresult", result);
					}
					
				}
				else {
					result = "密码错误";
					resultmap.put("failresult", result);
				}
			}
			else {
				result = "用户名不存在";
				resultmap.put("failresult", result);
			}
			this.close();
		} 
		catch (SQLException e) {
			result = "服务器异常";
			resultmap.put("failresult", result);
			e.printStackTrace();
		}
		return resultmap;		
	}*/
	
	public User getByID(String id) {
		String sql = "SELECT * FROM customers WHERE cust_id=?";
		rs = this.executeQuery(sql, id);
		try {
			if(rs.next()) {
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
			this.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}
