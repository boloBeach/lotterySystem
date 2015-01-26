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
	
	private String prizeNo;
	

	public String getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	private String[] ids;
	private String prizeName;
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		String personIDs = request.getParameter("ids");
		//String round = request.getParameter("round");
	    prizeNo = request.getParameter("prizeNo");
		prizeName = request.getParameter("prizeName");
		System.out.println("personIDs: " + personIDs);
		System.out.println("prizeNo: " + prizeNo);

		ids = null;
		if (personIDs != null) {
			ids = personIDs.split(",");
		}
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				System.out.println("¿ªÆôÏß³Ì");
				usersService.updateUsers(ids, prizeName);
				usersService.exportToExcel();
				if(prizeNo!=null){
					usersService.updatePrizeStatus(prizeNo);
				}
			}
		});
		thread.start();
		
		

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		List<UsersBean> listUsersBeans = usersService.getListUsersBeans();
		out.write(gson.toJson(listUsersBeans));
		out.close();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
