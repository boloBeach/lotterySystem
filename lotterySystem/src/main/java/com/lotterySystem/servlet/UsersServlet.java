package com.lotterySystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;

import com.google.gson.Gson;
import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.constant.Constants;
import com.lotterySystem.util.StringUtil;
import com.lotterySystem.util.XmlUtil;

public class UsersServlet extends HttpServlet {
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
		    ClassLoader classLoader = getClass().getClassLoader();
		    String xmlPath = classLoader.getResource(Constants.XML_FILE_PATH).getPath();
			XmlUtil xmlUtil = new XmlUtil(xmlPath);
			Document document = xmlUtil.readDocument();
			List<UsersBean> listUsersBeans = xmlUtil.getUsersByXml(document);
			out.write(gson.toJson(listUsersBeans));
		} else {
			String error = "sorry the start is null";
			out.write(gson.toJson(error));
		}
		out.close();
	}
}
