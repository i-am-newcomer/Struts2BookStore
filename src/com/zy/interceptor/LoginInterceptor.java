package com.zy.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zy.entity.User;

public class LoginInterceptor extends AbstractInterceptor {

	//登录拦截器，对用户是否已经登录进行验证
	public String intercept(ActionInvocation invocation) throws Exception {
		//String actionName = invocation.getProxy().getActionName();
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user == null) {
			return Action.LOGIN;
		}
		return invocation.invoke();
	} 

}
