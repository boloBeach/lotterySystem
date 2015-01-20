package com.lotterySystem.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.constant.Constants;

/**
 * 
 * @author royzhang
 * @see this is control xml file
 *
 */
@SuppressWarnings("unchecked")
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
	
	
	public static String[] prizeNames;
	public static Properties p = new Properties();
	static {
		//load the prize names configured in the properties file
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("prizeInfo.properties");
		try {
			p.load(inputStream);
			String pValue = p.getProperty("prizeNames");
			System.out.println("Properties from properties file: "+pValue);
			
			prizeNames = pValue.split(",");
			
			System.out.println("prizeNames: "+Arrays.asList(prizeNames));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
	}
	
	public Document readDocument() {
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
	 * 
	 * @return
	 */

	public List<UsersBean> getUsersByXml(Document document) {
		List<UsersBean> result = new ArrayList<UsersBean>();
		Element element = document.getRootElement(); // create root element
		List<Element> listElements = element.getChildren("RECORD");
		Element recod = null;
		Element id = null;
		Element englishName = null;
		Element chineseName = null;
		Element lastName = null;
		Element userImg = null;
		Element isDelete = null;
		String isdeleteString = null;
		for (int i = 0; i < listElements.size(); i++) {
			recod = listElements.get(i);
			isDelete = recod.getChild("is_delete");
			isdeleteString = isDelete.getText().trim();
			if (Integer.valueOf(isdeleteString) == 0) {
				id = recod.getChild("id");
				englishName = recod.getChild("english_name");
				chineseName = recod.getChild("chinese_name");
				lastName = recod.getChild("last_name");
				userImg = recod.getChild("user_img");
				UsersBean usersBean = new UsersBean();
				usersBean.setChineseName(chineseName.getText());
				usersBean.setEnglishName(englishName.getText());
				usersBean.setId(id.getText());
				usersBean.setIsDeleteString(isdeleteString);
				usersBean.setUserImg(userImg.getText());
				usersBean.setLastName(lastName.getText());
				result.add(usersBean);
			}
		}
		return result;
	}

	/**
	 * get the users who have prized
	 * 
	 * @param document
	 * @return
	 */
	public List<UsersBean> getPrizedUsersByXml(Document document) {
		List<UsersBean> result = new ArrayList<UsersBean>();
		Element element = document.getRootElement(); // create root element
		List<Element> listElements = element.getChildren("RECORD");
		Element recod = null;
		Element id = null;
		Element englishName = null;
		Element chineseName = null;
		Element lastName = null;
		Element userImg = null;
		Element isDelete = null;
		Element prizeType = null;
		String isdeleteString = null;
		for (int i = 0; i < listElements.size(); i++) {
			recod = listElements.get(i);
			isDelete = recod.getChild("is_delete");
			prizeType = recod.getChild("prize_type");
			isdeleteString = isDelete.getText().trim();
			if (!StringUtil.isNull(prizeType.getText())) {
				id = recod.getChild("id");
				englishName = recod.getChild("english_name");
				chineseName = recod.getChild("chinese_name");
				lastName = recod.getChild("last_name");
				userImg = recod.getChild("user_img");
				UsersBean usersBean = new UsersBean();
				usersBean.setChineseName(chineseName.getText());
				usersBean.setEnglishName(englishName.getText());
				usersBean.setId(id.getText());
				usersBean.setIsDeleteString(isdeleteString);
				usersBean.setUserImg(userImg.getText());
				usersBean.setPrizeType(prizeType.getText());
				usersBean.setLastName(lastName.getText());
				result.add(usersBean);
			}
		}
		return result;
	}

	/**
	 * update the xml files
	 * 
	 * @param id
	 *            this is userId
	 * @param document
	 *            this is XML document
	 * @param isDelete
	 *            it is must equal 1
	 * @param prize
	 *            price name
	 */
	public void updateXmlById(String id, Document document, String isDelete,
			String prizeType) throws Exception {

		System.out.println("id: " + id + ", Document:" + document
				+ ", prizeType: " + prizeType);

		Element element = document.getRootElement(); // create root element
		List<Element> listElements = element.getChildren("RECORD");
		Element elementId = null;
		for (Element record : listElements) {
			elementId = record.getChild("id");
			if (id.trim().equals(elementId.getText().trim())) {
				record.getChild("is_delete").setText(isDelete);

				Element prizeTypeEl = record.getChild("prize_type");
				String prizeString = prizeTypeEl.getText().toString();
				if (Constants.BIG_AWARD_NAME.equals(prizeString)
						&& !prizeType.equals(prizeString)) {
					record.getChild("prize_type").setText(
							prizeString + "," + prizeType);
				} else {
					record.getChild("prize_type").setText(prizeType);
				}
				XMLOutputter xmlOutputter = new XMLOutputter();
				xmlOutputter.output(document, new FileOutputStream(xmlFile));
			}
		}

	}


	private static Map<String, List<UsersBean>> initPrizeLevelAndUsersBeanListMap() {
		Map<String, List<UsersBean>> resultMap = new HashMap<String, List<UsersBean>>();
		for (int i = 0; i < prizeNames.length; i++) {
			resultMap.put(prizeNames[i], new ArrayList<UsersBean>());
		}
		return resultMap;
	}

	/**
	 * get prized users by prize level
	 * 
	 * @param document
	 * @return
	 */
	public static Map<String, List<UsersBean>> getAwardedUsersByXml(
			Document document) {
		Map<String, List<UsersBean>> resultMap = initPrizeLevelAndUsersBeanListMap();
		Set<String> keySet = resultMap.keySet();

		Element element = document.getRootElement(); // create root element
		List<Element> listElements = element.getChildren("RECORD");
		Element recod = null;
		Element id = null;
		Element englishName = null;
		Element chineseName = null;
		Element lastName = null;
		Element userImg = null;
		Element isDelete = null;
		Element prizeType = null;
		String isdeleteString = null;
		for (int i = 0; i < listElements.size(); i++) {
			recod = listElements.get(i);
			isDelete = recod.getChild("is_delete");
			prizeType = recod.getChild("prize_type");
			isdeleteString = isDelete.getText().trim();

			boolean contains = false;
			String text2 = prizeType.getText();
			if (text2 != null) {
				for (String pName : prizeNames) {
					if (text2.contains(pName)) {
						contains = true;
						break;
					}
				}
			}

			if (!StringUtil.isNull(prizeType.getText()) && contains) {
				id = recod.getChild("id");
				englishName = recod.getChild("english_name");
				chineseName = recod.getChild("chinese_name");
				lastName = recod.getChild("last_name");
				userImg = recod.getChild("user_img");
				UsersBean usersBean = new UsersBean();
				usersBean.setChineseName(chineseName.getText());
				usersBean.setEnglishName(englishName.getText());
				usersBean.setId(id.getText());
				usersBean.setIsDeleteString(isdeleteString);
				usersBean.setUserImg(userImg.getText());
				usersBean.setPrizeType(prizeType.getText());
				usersBean.setLastName(lastName.getText());

				String text = prizeType.getText();
				if (text != null && text.contains(Constants.BIG_AWARD_NAME)) {
					text = Constants.BIG_AWARD_NAME;
				}
				List<UsersBean> list = resultMap.get(text);
				list.add(usersBean);
			}
		}

		System.out.println("resultMap:" + resultMap);
		return resultMap;
	}
}
