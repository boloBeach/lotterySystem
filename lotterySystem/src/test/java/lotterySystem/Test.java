package lotterySystem;

import java.util.List;

import org.jdom.Document;

import com.lotterySystem.bean.UsersBean;
import com.lotterySystem.constant.Constants;
import com.lotterySystem.util.XmlUtil;

public class Test {
	public static void main(String[] args) {

		String xmlFile = Constants.XML_FILE_PATH;
		XmlUtil xmlUtil = new XmlUtil(xmlFile);
		Document document = xmlUtil.readDocument();
		try {
			xmlUtil.updateXmlById("CDH007", document, "1", "Ò»µÈ½±");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Document document1 = xmlUtil.readDocument();
		List<UsersBean> list = xmlUtil.getUsersByXml(document1);
		for (UsersBean usersBean : list) {
			System.out.println(usersBean.toString());
		}
	}

	public static void getSQl() {
		String tmp = "";
		String tmpString = "";
		for (int i = 8; i < 250; i++) {
			tmpString = i + "";
			if (tmpString.length() != 3) {
				for (int j = 0; j < 3 - tmpString.length(); j++) {
					tmp += "0";
				}
			}
			tmp += i;
			String insert = "insert into users(id,english_name,chinese_name,user_img) values(\"CDH0"
					+ tmp
					+ "\",\"roy"
					+ tmp
					+ "\",\"zhang"
					+ tmp
					+ "\",\"http://www.qqbody.com/uploads/allimg/120325/21292419A-9.jpg\")";
			System.out.print(";");
			tmp = "";
			System.out.println(insert);
		}
	}
}
