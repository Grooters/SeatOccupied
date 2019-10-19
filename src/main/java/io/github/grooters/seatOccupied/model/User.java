package io.github.grooters.seatOccupied.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {
	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// private int id;
	@NotNull
	private String name;
	@NotNull
	@Id
	private String number;
	@NotNull
	private String pass;
	// 昵称
	private String nickname;
	// 个性签名
	private String signature;
	@NotNull
	private String sex;
	@NotNull
	private String department;
	// 所占用座位的id，未占用座位即为空
	private int seatId;
	@NotNull
	private String major;
	// 占座时间
	private int time;
	// 入座次数
	@NotNull
	private int num;

	public User(String number, String name, String pass, String nickname, String signature, String nickName, String sex,
			String department, int seatId, String major, int time, int num) {
		this.nickname = nickName;
		this.name = name;
		this.number = number;
		this.pass = pass;
		this.nickname = nickname;
		this.signature = signature;
		this.sex = sex;
		this.department = department;
		this.seatId = seatId;
		this.major = major;
		this.time = time;
		this.num = num;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public User() {

	}

	// public int getId() {
	// return id;
	// }
	//
	// public void setId(int id) {
	// this.id = id;
	// }

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatid) {
		this.seatId = seatid;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
