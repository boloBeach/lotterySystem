package com.lotterySystem.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.service.UsersService;

public class GetGroupUsersInfoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String group = request.getParameter("group");
		request.setAttribute("group", group);
		request.getSession().setAttribute("group", group);
		
		UsersService usersService = new UsersService();
		List<UsersBean> usersBeans = usersService.getAllUsersBeans();
		
		List<UsersBean> bigAwardUsersBeans = usersService.getBigAwardUsersBeans();
		boolean alarm = false;
		if(bigAwardUsersBeans!=null&&bigAwardUsersBeans.size()>=4){
			alarm = true;
		}
		
		List<UsersBean> list=null;
		if("A".equalsIgnoreCase(group)){
			list = usersBeans.subList(0, 50);
		}else 
		if("B".equalsIgnoreCase(group)){
			list = usersBeans.subList(50, 100);
		}else
		if("C".equalsIgnoreCase(group)){
			list = usersBeans.subList(100, 150);
		}else
		if("D".equalsIgnoreCase(group)){
			list = usersBeans.subList(150, 200);
		}else
		if("E".equalsIgnoreCase(group)){
			list = usersBeans.subList(200, usersBeans.size());
		}
		if(list!=null){
			request.getSession().setAttribute("users", list);
			request.getSession().setAttribute("alarm", alarm);
			request.getSession().setAttribute("lowId",list.get(0).getId() );
			request.getSession().setAttribute("highId", list.get(list.size()-1).getId());
		}
		response.sendRedirect("bigaward.jsp");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
