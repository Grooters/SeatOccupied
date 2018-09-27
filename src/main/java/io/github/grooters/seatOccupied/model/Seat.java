package io.github.grooters.seatOccupied.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    //座位所在楼层
    private int floor;
    @NotNull
    //座位编号
    private int number;
    @NotNull
    //座位状态，0表示空闲，1表示占用
    private int idle;
    //座位拥有者学号
    private String userNumber;

    //入座的时间
    private int starttime;

    //入座累计时间
    private int time;

    public Seat(@NotNull int floor, @NotNull int number, @NotNull int idle, String userNumber, int starttime, int time) {
        this.floor = floor;
        this.number = number;
        this.idle = idle;
        this.userNumber = userNumber;
        this.starttime = starttime;
        this.time = time;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
