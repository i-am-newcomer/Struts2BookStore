package com.zy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckCodeInterceptor extends AbstractInterceptor {

	//验证码拦截器，对注册和登录时的验证码进行验证
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String checkcode = (String) session.getAttribute("checkcode");
		//System.out.println("checkcode: "+checkcode);
		String in_checkcode = request.getParameter("in_checkcode");
		//System.out.println("in_checkcode: "+in_checkcode);
		if(!checkcode.equals(in_checkcode)) {
			String checkcode_result = "验证码输入错误";
			request.setAttribute("checkcode_result", checkcode_result);
			return Action.INPUT;
		}
		return invocation.invoke();
	}

}
