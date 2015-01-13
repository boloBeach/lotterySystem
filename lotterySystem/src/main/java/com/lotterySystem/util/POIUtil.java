package com.lotterySystem.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.Document;

import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.constant.Constants;

public class POIUtil {

	public static String[] heads = { "id", "English Name", "Chinese Name",
			"Award level" };
	public static String filePath = "AwardedPerson.xls";
	public static Boolean clearFlag = true;

	public static void export() {
		try {
			HSSFWorkbook xls = new HSSFWorkbook();
			HSSFSheet hssfSheet = xls.createSheet("111");

			// head title row
			HSSFRow headRow = hssfSheet.createRow(0);
			for (int i = 0; i < heads.length; i++) {
				HSSFCell headCell = headRow.createCell(i);
				headCell.setCellValue(heads[i]);
			}
			// data here
			ClassLoader classLoader = new POIUtil().getClass().getClassLoader();
			String xmlPath = classLoader.getResource(Constants.XML_FILE_PATH)
					.getPath();
			XmlUtil xmlUtil = new XmlUtil(xmlPath);
			Document document = xmlUtil.readDocument();
			List<UsersBean> listUsersBeans = xmlUtil
					.getPrizedUsersByXml(document);

			int rowNum = 1;
			System.out.println("listUsersBeans:" + listUsersBeans.size());
			for (int i = 0; i < listUsersBeans.size(); i++) {
				UsersBean usersBean = listUsersBeans.get(i);
				String prizeType = usersBean.getPrizeType();
				System.out.println("prizeType: " + prizeType);
				if (!StringUtil.isNull(prizeType)) {
					HSSFRow childRow = hssfSheet.createRow(rowNum);
					HSSFCell idCell = childRow.createCell(0);
					HSSFCell enNameCell = childRow.createCell(1);
					HSSFCell chNameCell = childRow.createCell(2);
					HSSFCell prizeCell = childRow.createCell(3);
					idCell.setCellValue(usersBean.getId());
					enNameCell.setCellValue(usersBean.getEnglishName());
					chNameCell.setCellValue(usersBean.getChineseName());
					prizeCell.setCellValue(usersBean.getPrizeType());
					rowNum++;
				}
			}
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			File file = new File(path+filePath);
			System.out.println("Path: "+path+filePath);
			if(!file.exists()){
				file.createNewFile();
			}else if(clearFlag){
				file.delete();
				file.createNewFile();
			}
			
			OutputStream out = new FileOutputStream(file);
			xls.write(out);
			xls.close();
			System.out.println("Data export successful!");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
