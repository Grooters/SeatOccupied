package io.github.grooters.seatOccupied.model;

public class NowSeatMsg {

	private String name;

	// 座位编号
	private int seatId;

	// 累计时间
	private int totalTime;

	// 离座时间
	private String leaveTime;

	public NowSeatMsg() {
	}

	public NowSeatMsg(String name, int seatId, int totalTime, String leaveTime) {
		this.name = name;
		this.seatId = seatId;
		this.totalTime = totalTime;
		this.leaveTime = leaveTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
}