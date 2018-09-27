package io.github.grooters.seatOccupied.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
    @NotNull
    private String name;
    @NotNull
    @Id
    private String number;
    @NotNull
    private String pass;
    //昵称
    private String nickname;
    //个性签名
    private String signature;
    @NotNull
    private String sex;
    @NotNull
    private String department;
    //所占用座位的id，未占用座位即为空
    private String seatId;
    private String major;
    //占座时间
    private int time;
    //入座次数
    private int seatnumber;

    public User(String name, String nickName,String sex, String department, String seatId, String major,int time,int seatnumber) {
        this.nickname=nickName;
        this.name = name;
        this.sex = sex;
        this.department = department;
        this.seatId = seatId;
        this.major = major;
        this.time=time;
        this.seatnumber=seatnumber;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(int seatnumber) {
        this.seatnumber = seatnumber;
    }

    public User(){

    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
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
