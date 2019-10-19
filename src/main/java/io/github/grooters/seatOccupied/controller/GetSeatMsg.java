package io.github.grooters.seatOccupied.controller;

import com.alibaba.fastjson.JSON;

import io.github.grooters.seatOccupied.dao.SeatDao;
import io.github.grooters.seatOccupied.model.NowSeatMsg;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.Seat;

import io.github.grooters.seatOccupied.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


@RestController
public class GetSeatMsg {
	@Autowired
	private UserDao userDao;
	@Autowired
	private SeatDao seatDao;
	public static final int FAILED = 0;

	@RequestMapping(value = { "/getseatmsg" }, method = RequestMethod.GET)
	public String geSeatmsg(String usernumber) {
		System.out.println("geSeatmsg_usernumber:"+usernumber);
		User user = userDao.findByNumber(usernumber);
		Seat seat = seatDao.findById(user.getSeatId());
		if (user.getSeatId() != 0) {
			NowSeatMsg seatmsg = new NowSeatMsg(user.getName(), seat.getId(), seat.getTime(), seat.getLeavetime());
			return JSON.toJSONString(seatmsg);
		}
		return JSON.toJSONString(FAILED);
	}

	// test
	@RequestMapping(value = { "/test" }, method = RequestMethod.GET)
	public void list(int minutes) {
//		Calendar calendar=Calendar.getInstance();
//		calendar.setTime(new Date());
//		return String.valueOf(calendar.get(Calendar.MINUTE));
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("1");
			}
		},minutes);
	}
}