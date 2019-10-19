package io.github.grooters.seatOccupied.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "main_post")
public class MainNote {
	@Id
	private int id;
	@NotNull
	// 发帖人名称
	private String userName;
	@NotNull
	// 发帖人学号
	private String userNumber;
	@NotNull
	// 帖子标题
	private String title;
	@NotNull
	// 帖子的点赞数
	private int praiseNum;
	// 帖子发表日期
	@NotNull
	private String date;
	// 帖子内容
	@NotNull
	private String content;

	public MainNote() {
	}

	public MainNote(@NotNull String userName, @NotNull String userNumber, @NotNull String title, @NotNull int praiseNum,
			@NotNull String date, @NotNull String content) {
		this.userName = userName;
		this.userNumber = userNumber;
		this.title = title;
		this.praiseNum = praiseNum;
		this.date = date;
		this.content = content;
	}

	public int getId() {
		return id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(int praiseNum) {
		this.praiseNum = praiseNum;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
