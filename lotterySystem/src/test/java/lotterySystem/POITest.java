package lotterySystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
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
	public static Boolean clearFlag = true;

	public static void main(String[] args) {
		export();
	}

	public static void export() {
		try {
			HSSFWorkbook xls = new HSSFWorkbook();
			HSSFSheet hssfSheet = xls.createSheet();
			for (int i = 0; i < 4; i++) {
				hssfSheet.setColumnWidth(i, 4500);
			}
			HSSFCellStyle style = xls.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFRow introRow = hssfSheet.createRow(0);
			HSSFCell introCell = introRow.createCell(0);

			introCell.setCellStyle(style);
			introCell
					.setCellValue("Award level explain: 0[特等奖],1[一等奖],2[二等奖],3[三等奖]");
			hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			// head title row
			HSSFRow headRow = hssfSheet.createRow(1);
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
			Map<String, List<UsersBean>> listUsersBeansMap = getUsersByXml(document);

			int rowNum = 2;
			Collection<List<UsersBean>> values = listUsersBeansMap.values();

			for (List<UsersBean> listUsersBeans : values) {
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
	
	
	private static Map<String, List<UsersBean>> initPrizeLevelAndUsersBeanListMap(){
		Map<String, List<UsersBean>> resultMap = new HashMap<String, List<UsersBean>>();
		resultMap.put("0", new ArrayList<UsersBean>());
		resultMap.put("1", new ArrayList<UsersBean>());
		resultMap.put("2", new ArrayList<UsersBean>());
		resultMap.put("3", new ArrayList<UsersBean>());
		
		return resultMap;
	}
	
	

	public static Map<String, List<UsersBean>> getUsersByXml(Document document) {
		Map<String, List<UsersBean>> resultMap = initPrizeLevelAndUsersBeanListMap();
		Set<String> keySet = resultMap.keySet();

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

			boolean contains = keySet.contains(prizeType.getText());
			if (!StringUtil.isNull(prizeType.getText()) && contains) {
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
				List<UsersBean> list = resultMap.get(prizeType.getText());
				list.add(usersBean);
			}
		}
		return resultMap;
	}
}
