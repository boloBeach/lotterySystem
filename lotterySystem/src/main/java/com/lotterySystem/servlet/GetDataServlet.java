package com.lotterySystem.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lotterySystem.service.UsersService;

public class GetDataServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsersService usersService;
	@Override
	public void init() throws ServletException {
		usersService = new UsersService();
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String personIDs = request.getParameter("ids");
		String prizeType = request.getParameter("prizeType");
		System.out.println("personIDs: "+personIDs);
		System.out.println("prizeType: "+prizeType);
		
		String[] ids = null;
		if(personIDs!=null){
			ids = personIDs.split(",");
		}
		usersService.updateUsers(ids, prizeType);
		usersService.exportToExcel();
	}
}
