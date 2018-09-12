package com.zy.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.zy.entity.User;
import com.zy.services.UserServices;

public class UserAction extends ActionSupport {
	private User user;
	private UserServices userservices = new UserServices();
	
	public String register() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String reg_checkcode = (String) request.getParameter("register_checkcode");
		HttpSession session = request.getSession();
		String checkcode = (String) session.getAttribute("checkcode");
		String result = userservices.register(user, checkcode, reg_checkcode);
		//String result = userservices.register(user);
		if(result.equals("true")) {
			System.out.println("register success");
			return Action.SUCCESS;
		}
		else {
			request.setAttribute("register_result", result);
			System.out.println("register fail");
			return Action.ERROR;
		}
	}
	
	public String beforeLogin() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(session.getAttribute("user")!=null) {
			return Action.SUCCESS;
		}
		else {
			return Action.LOGIN;
		}
	}
	
	public String login() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("login_name");
		String pwd = request.getParameter("login_pwd");
		String login_checkcode = request.getParameter("login_checkcode");
		HttpSession session = request.getSession();
		String checkcode = (String)session.getAttribute("checkcode");
		Map<String, String> resultMap = userservices.login(name, pwd, login_checkcode, checkcode);
		if(resultMap.get("successresult")!=null) {
			user = userservices.getByID(resultMap.get("successresult"));
			session.setAttribute("user", user);
			return Action.SUCCESS;			
		}
		request.setAttribute("login_result", resultMap.get("failresult"));
		return Action.LOGIN;
	}
	
	public String logout() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("user", null);
		return Action.LOGIN;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
