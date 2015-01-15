package com.lotterySystem.bean;

public class PrizeInfoBean {
	private String round;
	private String prizeName;
	private String prizePicName;
	private double prizedPersonNum;
	private double prizeTotal;
	private String prizeNo;
	private Integer prizeStatus;

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
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

	public double getPrizeTotal() {
		return prizeTotal;
	}

	public void setPrizeTotal(double prizeTotal) {
		this.prizeTotal = prizeTotal;
	}

	public String getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	public Integer getPrizeStatus() {
		return prizeStatus;
	}

	public void setPrizeStatus(Integer prizeStatus) {
		this.prizeStatus = prizeStatus;
	}

	@Override
	public String toString() {
		return "PrizeInfoBean [round=" + round + ", prizeName=" + prizeName
				+ ", prizePicName=" + prizePicName + ", prizedPersonNum="
				+ prizedPersonNum + ", prizeTotal=" + prizeTotal + ", prizeNo="
				+ prizeNo + ", prizeStatus=" + prizeStatus + "]";
	}

}
