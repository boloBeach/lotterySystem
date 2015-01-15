package com.lotterySystem.bean;

public class PrizeInfoBean {
	private String prizeLevel;
	private String prizeName;
	private String prizePicName;
	private double prizedPersonNum;

	public String getPrizeLevel() {
		return prizeLevel;
	}

	public void setPrizeLevel(String prizeLevel) {
		this.prizeLevel = prizeLevel;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getPrizePicName() {
		return prizePicName;
	}

	public void setPrizePicName(String prizePicName) {
		this.prizePicName = prizePicName;
	}

	public double getPrizedPersonNum() {
		return prizedPersonNum;
	}

	public void setPrizedPersonNum(double prizedPersonNum) {
		this.prizedPersonNum = prizedPersonNum;
	}

	@Override
	public String toString() {
		return "PrizeInfoBean [prizeLevel=" + prizeLevel + ", prizeName="
				+ prizeName + ", prizePicName=" + prizePicName
				+ ", prizedPersonNum=" + prizedPersonNum + "]";
	}

}
