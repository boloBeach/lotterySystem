package com.lotterySystem.service;

import java.util.List;

import org.jdom.Document;

import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.constant.Constants;
import com.lotterySystem.util.POIUtil;
import com.lotterySystem.util.XmlUtil;

public class UsersService {
	public List<UsersBean> getListUsersBeans() {
		ClassLoader classLoader = getClass().getClassLoader();
		String xmlPath = classLoader.getResource(Constants.XML_FILE_PATH)
				.getPath();
		System.out.println("xmlPath: "+xmlPath);
		
		XmlUtil xmlUtil = new XmlUtil(xmlPath);
		Document document = xmlUtil.readDocument();
		List<UsersBean> listUsersBeans = xmlUtil.getUsersByXml(document);
		return listUsersBeans;
	}

	public void updateUsers(String[] ids, String prizeType) {
		ClassLoader classLoader = getClass().getClassLoader();
		String xmlPath = classLoader.getResource(Constants.XML_FILE_PATH)
				.getPath();
		XmlUtil xmlUtil = new XmlUtil(xmlPath);
		Document document = xmlUtil.readDocument();
		for (int i = 0; ids != null && i < ids.length; i++) {
			String id = ids[i];
			try {
				xmlUtil.updateXmlById(id, document, "1", prizeType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public void exportToExcel() {
		POIUtil.export();
	}
}
