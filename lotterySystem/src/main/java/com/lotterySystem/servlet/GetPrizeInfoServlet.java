package com.lotterySystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lotterySystem.bean.PrizeInfoBean;
import com.lotterySystem.util.POIUtil;

public class GetPrizeInfoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/json; charset=UTF-8");
//		resp.setHeader("Cache-Control", "no-cache");
//		resp.setHeader("Pragma", "no-cache");
		PrintWriter out = resp.getWriter();
		Gson gson = new Gson();
		 List<PrizeInfoBean> readXls = POIUtil.readXls();
		 
		 System.out.println(readXls.size());
		out.write(gson.toJson(readXls));
		out.close();

	}
}
