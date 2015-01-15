package com.lotterySystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lotterySystem.bean.UsersBean;
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
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		String personIDs = request.getParameter("ids");
		String prizeType = request.getParameter("prizeType");
		String prizeName = request.getParameter("prizeName");
		System.out.println("personIDs: " + personIDs);
		System.out.println("prizeType: " + prizeType);

		String[] ids = null;
		if (personIDs != null) {
			ids = personIDs.split(",");
		}
		usersService.updateUsers(ids, prizeType);
		usersService.exportToExcel();

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		List<UsersBean> listUsersBeans = usersService.getListUsersBeans();
		out.write(gson.toJson(listUsersBeans));
		out.close();
	}
}
