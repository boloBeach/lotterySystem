package com.lotterySystem.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.lotterySystem.bean.UsersBean;

/**
 * 
 * @author royzhang
 * @see this is control xml file
 *
 */
public class XmlUtil {
	
	private String xmlFile;
	
	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public XmlUtil(String xmlFile) {
		setXmlFile(xmlFile);
	}
	
	public Document readDocument(){
		SAXBuilder builder = new SAXBuilder();
		try {
			return builder.build(new File(xmlFile));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * get users by xml with xml prize_type is null or id_delete is 0
	 * @return
	 */
	public List<UsersBean> getUsersByXml(Document document){
		List<UsersBean> result = new ArrayList<UsersBean>();
		Element element = document.getRootElement(); // create root element
		List<Element> listElements = element.getChildren("RECORD");
		Element recod = null;
		Element id = null;
		Element englishName = null;
		Element chineseName = null;
		Element userImg = null;
		Element isDelete = null;
		String isdeleteString = null;
		for (int i = 0; i < listElements.size(); i++) {
			recod = listElements.get(i); 
			isDelete = recod.getChild("is_delete");
			isdeleteString = isDelete.getText().trim();
			if(Integer.valueOf(isdeleteString)==0){
				id = recod.getChild("id");
				englishName = recod.getChild("english_name");
				chineseName = recod.getChild("chinese_name");
				userImg = recod.getChild("user_img");
				UsersBean usersBean = new UsersBean();
				usersBean.setChineseName(chineseName.getText());
				usersBean.setEnglishName(englishName.getText());
				usersBean.setId(id.getText());
				usersBean.setIsDeleteString(isdeleteString);
				usersBean.setUserImg(userImg.getText());
				result.add(usersBean);
			}
		}
		return result;
	}
	
	/**
	 * update the xml files
	 * @param id  this is userId
	 * @param document this is XML document
	 * @param isDelete it is must equal 1
	 * @param prize  price name
	 */
	public void updateXmlById(String id,Document document,String isDelete,String prizeType) throws Exception{
		Element element = document.getRootElement(); // create root element
		List<Element> listElements = element.getChildren("RECORD");
		Element elementId = null;
		for (Element record : listElements) {
			elementId = record.getChild("id");
			if(id.trim().equals(elementId.getText().trim())){
				record.getChild("is_delete").setText(isDelete);
				record.getChild("prize_type").setText(prizeType);
				XMLOutputter xmlOutputter = new XMLOutputter();
				xmlOutputter.output(document, new FileOutputStream(xmlFile));
			}
		}
		
	}
	
}
