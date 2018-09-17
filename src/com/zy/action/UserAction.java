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
	private String logName;
	private String logPwd;
	
	public void validateRegister() {
		String result = userservices.check_reguser(user);
		if(!result.equals("true")) {
			this.addActionError(result);
		}
	}
	
	public String register() {
		int result = userservices.register(user);
		if(result>0) {
			System.out.println("register success");
			return Action.SUCCESS;
		}
		return Action.INPUT;
			
/*		HttpServletRequest request = ServletActionContext.getRequest();
		String reg_checkcode = (String) request.getParameter("register_checkcode");
		HttpSession session = request.getSession();
		String checkcode = (String) session.getAttribute("checkcode");
		String result = userservices.register(user, checkcode, reg_checkcode);
		if(result.equals("true")) {
			System.out.println("register success");
			return Action.SUCCESS;
		}
		else {
			request.setAttribute("register_result", result);
			System.out.println("register fail");
			return Action.ERROR;
		}*/
	}
	
	public String beforeLogin() {
		return Action.SUCCESS;
	}
	
	public void validateLogin() {
		String result = userservices.check_loguser(logName, logPwd);
		if(!result.equals("true")) {
			this.addActionError(result);
		}
	}
	
	public String login() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = userservices.login(logName, logPwd);
		if(user != null) {
			session.setAttribute("user", user);
			return Action.SUCCESS;
		}
		return Action.LOGIN;
		
/*		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String login_checkcode = request.getParameter("login_checkcode");
		String checkcode = (String)session.getAttribute("checkcode");
		Map<String, String> resultMap = userservices.login(logName, logPwd, login_checkcode, checkcode);
		if(resultMap.get("successresult")!=null) {
			user = userservices.getByID(resultMap.get("successresult"));
			session.setAttribute("user", user);
			return Action.SUCCESS;			
		}
		else {
			request.setAttribute("login_result", resultMap.get("failresult"));
			return Action.LOGIN;
		}
		*/
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

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogPwd() {
		return logPwd;
	}

	public void setLogPwd(String logPwd) {
		this.logPwd = logPwd;
	}
	
	

}
