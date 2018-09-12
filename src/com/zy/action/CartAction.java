package com.zy.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.zy.entity.Book;
import com.zy.entity.OrderItem;
import com.zy.entity.User;
import com.zy.services.ShoppingCartServices;

public class CartAction {
	private User user;
	private ShoppingCartServices cartservices = new ShoppingCartServices();
	Map<OrderItem, Book> cartMap;
	
	public String addCart() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		user = (User)session.getAttribute("user");
		if(user == null) {
			return Action.LOGIN;
		}
		String book_id = request.getParameter("book_id");
		String book_quantity = request.getParameter("book_quantity");
		String cust_id = String.valueOf(user.getId());
		int result = cartservices.add(book_id, book_quantity, cust_id);
		if(result != -1) {
			return Action.SUCCESS;
		}
		return Action.ERROR;
	}
	
	public String getCart() {
		user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user != null) {
			String cust_id = String.valueOf(user.getId());
			cartMap = cartservices.getOrderMap(cust_id);
			return Action.SUCCESS;
		}
		return Action.LOGIN;
		
		
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCartMap(Map<OrderItem, Book> cartMap) {
		this.cartMap = cartMap;
	}

	public Map<OrderItem, Book> getCartMap() {
		return cartMap;
	}
	
	
	

	
}
