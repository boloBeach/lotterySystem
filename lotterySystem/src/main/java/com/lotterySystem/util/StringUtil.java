package com.lotterySystem.util;

public class StringUtil {
	/**
	 * if the param is null or param is empty thus return ture,
	 * thus false
	 * @param param
	 * @return boolean
	 */
	public static boolean isNull(String param){
		if(param == null || param.isEmpty())
			return true;
		return false;
	}
}
