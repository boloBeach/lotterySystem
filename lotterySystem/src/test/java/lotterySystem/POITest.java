package lotterySystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;

import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.constant.Constants;
import com.lotterySystem.util.StringUtil;
import com.lotterySystem.util.XmlUtil;

public class POITest {

	public static String[] heads = { "id", "English Name", "Chinese Name",
			"Award level" };
	public static String filePath = "AwardedPerson.xls";

	public static void main(String[] args) {
		export();
	}

	public static void export() {
		InputStream in;
		try {
			in = new FileInputStream(filePath);
			HSSFWorkbook xls = new HSSFWorkbook(in);
			HSSFSheet hssfSheet = xls.getSheetAt(0);

			int lastRowNum = hssfSheet.getLastRowNum();
			if (lastRowNum <= 0) {
				lastRowNum = 0;
			}
			// head title row
			HSSFRow headRow = hssfSheet.createRow(0);
			for (int i = 0; i < heads.length; i++) {
				HSSFCell headCell = headRow.createCell(i);
				headCell.setCellValue(heads[i]);
			}
			// data here
			ClassLoader classLoader = new POITest().getClass().getClassLoader();
			String xmlPath = classLoader.getResource(Constants.XML_FILE_PATH)
					.getPath();
			XmlUtil xmlUtil = new XmlUtil(xmlPath);
			Document document = xmlUtil.readDocument();
			List<UsersBean> listUsersBeans = xmlUtil
					.getPrizedUsersByXml(document);

			int rowNum = 1 + lastRowNum;
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
			OutputStream out = new FileOutputStream(filePath);

			xls.write(out);
			xls.close();
			System.out.println("Data export successful!");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<UsersBean> getUsersByXml(Document document) {
		List<UsersBean> result = new ArrayList<UsersBean>();
		Element element = document.getRootElement(); // create root element
		List<Element> listElements = element.getChildren("RECORD");
		Element recod = null;
		Element id = null;
		Element englishName = null;
		Element chineseName = null;
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
				userImg = recod.getChild("user_img");
				UsersBean usersBean = new UsersBean();
				usersBean.setChineseName(chineseName.getText());
				usersBean.setEnglishName(englishName.getText());
				usersBean.setId(id.getText());
				usersBean.setIsDeleteString(isdeleteString);
				usersBean.setUserImg(userImg.getText());
				usersBean.setPrizeType(prizeType.getText());
				result.add(usersBean);
			}
		}
		return result;
	}
}
