package com.lotterySystem.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jdom.Document;

import com.lotterySystem.bean.PrizeInfoBean;
import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.constant.Constants;

public class POIUtil {

	public static String[] heads = { "id", "English Name", "Chinese Name",
			"Award-Name" };
	public static String filePath = "AwardedPerson.xls";
	public static Boolean clearFlag = true;

	public static String prizeXlsName = "prizeDisplay.xls";

	public static void main(String[] args) {
		export1();
		// List<PrizeInfoBean> map = readXls();
		// for (PrizeInfoBean prizeInfoBean : map) {
		// System.out.println(prizeInfoBean);
		// }
	}

	/**
	 * Read the prize information from a excel file
	 * 
	 * @return
	 */
	public static List<PrizeInfoBean> readXls() {
		List<PrizeInfoBean> prizeInfoBeanList = new ArrayList<PrizeInfoBean>();
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();

		String realPath = path + prizeXlsName;

		File file = new File(realPath);
		if (!file.exists()) {
			try {
				throw new FileNotFoundException("file doesn't found!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println("realPath: "+realPath);
		HSSFWorkbook wbook = null;
		try {
			InputStream in = new FileInputStream(file);
			wbook = new HSSFWorkbook(in);

			HSSFSheet prizeSheet = wbook.getSheetAt(0);
			int rows = prizeSheet.getLastRowNum();
			rows= prizeSheet.getPhysicalNumberOfRows();
			System.out.println("rows: " + rows);
			for (int i = 1; i < rows; i++) {
				HSSFRow prizeRow = prizeSheet.getRow(i);
				short cells = prizeRow.getLastCellNum();

				PrizeInfoBean prizeInfoBean = new PrizeInfoBean();
				for (int j = 0; j < cells; j++) {
					HSSFCell prizeCell = prizeRow.getCell(j);
					int cellType = prizeCell.getCellType();

					if (HSSFCell.CELL_TYPE_NUMERIC == cellType) {
						double value = prizeCell.getNumericCellValue();

						if (j == 0) {
							prizeInfoBean.setRound(String.valueOf(value));
						} else if (j == 3) {
							prizeInfoBean.setPrizedPersonNum(value);
						} else if (j == 4) {
							prizeInfoBean.setPrizeTotal(value);
						} else if (j == 5) {
							prizeInfoBean.setPrizeNo(String.valueOf(value));
						} else if (j == 6) {
							Double d= Double.valueOf(value);
							int intValue = d.intValue();
							if (intValue == 0) {
								prizeInfoBean.setPrizeStatus(0);
							} else if (intValue == 1) {
								prizeInfoBean.setPrizeStatus(1);
							}
						}
					} else if (HSSFCell.CELL_TYPE_STRING == cellType) {
						String value = prizeCell.getStringCellValue();
						if (j == 1) {
							prizeInfoBean.setPrizeName(value);
						} else if (j == 2) {
							prizeInfoBean.setPrizePicName(value);
						}
					}
				}
				if (prizeInfoBean.getPrizeStatus().equals(0)) {
					prizeInfoBeanList.add(prizeInfoBean);
				}
			}

			wbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return prizeInfoBeanList;
	}

	public static void updatePrizeStatus(String prizeNo) {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		String realPath = path + prizeXlsName;
		File file = new File(realPath);
		if (!file.exists()) {
			try {
				throw new FileNotFoundException("file doesn't found!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println("PeizeInfoExcel: " + realPath);
		HSSFWorkbook wbook = null;
		try {
			InputStream in = new FileInputStream(file);
			wbook = new HSSFWorkbook(in);

			HSSFSheet prizeSheet = wbook.getSheetAt(0);
			Double doubleValue = Double.valueOf(prizeNo);
			int intValue = doubleValue.intValue();
			System.out.println("Update row: " + intValue);
			HSSFRow row = prizeSheet.getRow(intValue);
			HSSFCell cell = row.getCell(6);
			cell.setCellValue(1);

			OutputStream out = new FileOutputStream(file);

			wbook.write(out);
			wbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * export the awarded person list
	 */
	public static void export() {
		try {
			HSSFWorkbook xls = new HSSFWorkbook();
			HSSFSheet hssfSheet = xls.createSheet("award");

			for (int i = 0; i < 4; i++) {
				hssfSheet.setColumnWidth(i, 4500);
			}
			HSSFCellStyle style = xls.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFRow introRow = hssfSheet.createRow(0);
			HSSFCell introCell = introRow.createCell(0);

			introCell.setCellStyle(style);
			introCell.setCellValue("Awarded person list");
			hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			// head title row
			HSSFRow headRow = hssfSheet.createRow(1);
			for (int i = 0; i < heads.length; i++) {
				HSSFCell headCell = headRow.createCell(i);
				headCell.setCellValue(heads[i]);
			}
			// data here
			ClassLoader classLoader = new POIUtil().getClass().getClassLoader();
			classLoader = Thread.currentThread().getContextClassLoader();
			String xmlPath = classLoader.getResource(Constants.XML_FILE_PATH)
					.getPath();
			XmlUtil xmlUtil = new XmlUtil(xmlPath);
			Document document = xmlUtil.readDocument();
			List<UsersBean> prizedUsers = xmlUtil.getPrizedUsersByXml(document);

			int rowNum = 2;

			for (int i = 0; i < prizedUsers.size(); i++) {
				UsersBean usersBean = prizedUsers.get(i);
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

			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
			File file = new File(path + filePath);
			System.out.println("Path: " + path + filePath);
			if (!file.exists()) {
				file.createNewFile();
			} else if (clearFlag) {
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

	@SuppressWarnings("static-access")
	public static void export1() {
		try {
			HSSFWorkbook xls = new HSSFWorkbook();
			HSSFSheet hssfSheet = xls.createSheet("award");

			for (int i = 0; i < 4; i++) {
				hssfSheet.setColumnWidth(i, 4500);
			}
			HSSFCellStyle style = xls.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFRow introRow = hssfSheet.createRow(0);
			HSSFCell introCell = introRow.createCell(0);

			introCell.setCellStyle(style);
			introCell.setCellValue("Awarded Infomation");
			hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			// head title row
			HSSFRow headRow = hssfSheet.createRow(1);
			for (int i = 0; i < heads.length; i++) {
				HSSFCell headCell = headRow.createCell(i);
				headCell.setCellValue(heads[i]);
			}
			// data here
			ClassLoader classLoader = new POIUtil().getClass().getClassLoader();
			classLoader = Thread.currentThread().getContextClassLoader();
			String xmlPath = classLoader.getResource(Constants.XML_FILE_PATH)
					.getPath();
			System.out.println("========xmlPath1========"+xmlPath);
			XmlUtil xmlUtil = new XmlUtil(xmlPath);
			Document document = xmlUtil.readDocument();
			Map<String, List<UsersBean>> listUsersBeansMap = xmlUtil
					.getAwardedUsersByXml(document);

			int rowNum = 2;
			Collection<List<UsersBean>> values = listUsersBeansMap.values();

			for (List<UsersBean> listUsersBeans : values) {
				for (int i = 0; i < listUsersBeans.size(); i++) {
					UsersBean usersBean = listUsersBeans.get(i);
					String prizeType = usersBean.getPrizeType();
					// System.out.println("prizeType: " + prizeType);
					if (!StringUtil.isNull(prizeType)) {
						HSSFRow childRow = hssfSheet.createRow(rowNum);
						HSSFCell idCell = childRow.createCell(0);
						HSSFCell enNameCell = childRow.createCell(1);
						HSSFCell laNameCell = childRow.createCell(2);
						HSSFCell chNameCell = childRow.createCell(3);
						HSSFCell prizeCell = childRow.createCell(4);
						idCell.setCellValue(usersBean.getId());
						enNameCell.setCellValue(usersBean.getEnglishName());
						laNameCell.setCellValue(usersBean.getLastName());
						chNameCell.setCellValue(usersBean.getChineseName());
						prizeCell.setCellValue(usersBean.getPrizeType());
						rowNum++;
					}
				}
			}

			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
			File file = new File(path + filePath);
			System.out.println("Path: " + path + filePath);
			if (!file.exists()) {
				file.createNewFile();
			} else if (clearFlag) {
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
