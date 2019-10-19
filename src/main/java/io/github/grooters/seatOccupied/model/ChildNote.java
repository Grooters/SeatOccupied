package io.github.grooters.seatOccupied.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "child_post")
public class ChildNote {
	@Id
	private int id;
	@NotNull
	// 发表子帖者姓名
	private String userName;
	@NotNull
	// 发表子帖者学号
	private String userNumber;
	@NotNull
	// 子帖内容
	private String content;
	// 子帖发表日期
	@NotNull
	private String date;
	@NotNull
	// 子帖所属帖子
	private int mainPostId;
	@NotNull
	private int praiseNum;

	public ChildNote() {
	}


	public ChildNote(@NotNull String userName, @NotNull String userNumber, @NotNull String content, @NotNull String date, @NotNull int mainPostId,int praiseNum) {
		this.id = id;
		this.userName = userName;
		this.userNumber = userNumber;
		this.content = content;
		this.date = date;
		this.mainPostId = mainPostId;
		this.praiseNum = praiseNum;
	}

	public int getId() {
		return id;
	}

	public int getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(int praiseNum) {
		this.praiseNum = praiseNum;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getMainPostId() {
		return mainPostId;
	}

	public void setMainPostId(int mainPostId) {
		this.mainPostId = mainPostId;
	}

}
