package io.github.grooters.seatOccupied.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.github.grooters.seatOccupied.dao.SeatDao;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.AllSeat;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class LeaveSeat {

	private static final String SUCCESS = "1";
	private static final String FAILED = "0";

	@Autowired
	private SeatDao seatDao;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = { "/leaveseat" }, method = RequestMethod.GET)
	public String LSeat(String usernumber) throws ParseException {

		User user = userDao.findByNumber(usernumber);
		Seat seat = seatDao.findById(user.getSeatId());
		if(seat==null){
			return FAILED;
		}
		System.out.println("seat_idle:"+seat.getIdle());
		if (seat.getIdle() == 1 || seat.getIdle() == 3) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String letime = seat.getLeavetime();
			Date nowtime = new Date();// 当前时间
			String letime1 = df.format(nowtime);
			String[] letime2 = letime1.split(" ");
			letime = letime2[0] + " " + letime;
			Date ledate = df.parse(letime);// 预设时间
			int minute = user.getTime();// 总时长
			int mins = seat.getTime();// 预设的上座时间
			long fromtime = nowtime.getTime();
			long totime = ledate.getTime();
			mins = mins - (int) ((totime - fromtime) / (1000 * 60));// 得到这次上座了多少分钟

			seat.setIdle(0);
			user.setSeatId(0);
			seat.setLeavetime("");
			seat.setTime(0);
			seat.setUserNumber("");
			user.setTime(minute + mins);
			seatDao.save(seat);
			userDao.save(user);
			ScanSeat.count = 1;
			return JSON.toJSONString(user);
		} else
			return FAILED;
	}
}
