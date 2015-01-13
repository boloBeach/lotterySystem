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
import com.lotterySystem.util.StringUtil;

public class UsersServlet extends HttpServlet {
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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		String start = request.getParameter("start");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		if (!StringUtil.isNull(start)) {
			List<UsersBean> listUsersBeans = usersService.getListUsersBeans();
			out.write(gson.toJson(listUsersBeans));
		} else {
			String error = "sorry the start is null";
			out.write(gson.toJson(error));
		}
		out.close();
	}
}
