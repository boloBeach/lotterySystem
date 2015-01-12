package com.lotterySystem.bean;

public class UsersBean {
	private String id;
	private String englishName;
	private String chineseName;
	private String userImg;
	private String prizeType;
	private String isDeleteString;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getPrizeType() {
		return prizeType;
	}
	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}
	public String getIsDeleteString() {
		return isDeleteString;
	}
	public void setIsDeleteString(String isDeleteString) {
		this.isDeleteString = isDeleteString;
	}
	@Override
	public String toString() {
		return "UsersBean [id=" + id + ", englishName=" + englishName
				+ ", chineseName=" + chineseName + ", userImg=" + userImg
				+ ", prizeType=" + prizeType + ", isDeleteString="
				+ isDeleteString + "]";
	}
	
	
}
