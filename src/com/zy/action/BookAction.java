package com.zy.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.zy.entity.Book;
import com.zy.services.BookServices;

public class BookAction {
	private Book book;
	private BookServices bookservices = new BookServices() ;
	
	public String showbook() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String idstr = request.getParameter("book_id"); 
		System.out.println(idstr);
		book = bookservices.getByID(idstr);
		if(book != null) {
			//request.setAttribute("book", book);
			return Action.SUCCESS;
		}
		return Action.ERROR;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
