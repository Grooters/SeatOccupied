package io.github.grooters.seatOccupied.controller;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.github.grooters.seatOccupied.dao.SeatDao;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;

@RestController
public class OrderSeat {
	public static final int SUCCESS = 1;
	public static final int FAILED = 0;
	public static final int NONE = -1;
	@Autowired
	private SeatDao seatDao;
	@Autowired
	private UserDao userDao;
	OverTimer overTimer;

	@RequestMapping(value = "/orderseat", method = RequestMethod.GET)
	public int orderSeat(int seatid, String usernumber) {
		Seat seat = seatDao.findById(seatid);
		User user = userDao.findByNumber(usernumber);
		if (seat != null) {
			if (seat.getIdle() == 1 || seat.getIdle() == 2) {
				// 若座位已经被预约或已被占返回0
				return FAILED;
			} else if (user.getSeatId() != 0) {
				return FAILED;
			} else {
				seat.setIdle(2);
				seat.setUserNumber(usernumber);
				seatDao.save(seat);

				user.setSeatId(seatid);
				userDao.save(user);
				overTimer = new OverTimer(seat, user);
				// 预约成功返回1
				return SUCCESS;
			}
		}
		// 所选座位不存在返回-1
		return NONE;
	}

	@RequestMapping(value = "/unorderseat", method = RequestMethod.GET)
	public int unorderseat(String usernumber, int seatid) {
		User user = userDao.findByNumber(usernumber);
		Seat seat = null;
		// 用户有上座或有预约的情况
		if (user.getSeatId() != 0) {
			// 用户的座位id与传入的seatid相同
			if (seatid == user.getSeatId()) {
				seat = seatDao.findById(user.getSeatId());
				if (seat.getIdle() == 2) {
					seat.setIdle(0);
					seat.setUserNumber("");
					user.setSeatId(0);
					seatDao.save(seat);
					userDao.save(user);
					return SUCCESS;
				} else
					return FAILED;
			} else
				return FAILED;
		}
		// 用户无上座或预约
		else
			return FAILED;
	}

	class OverTimer {
		Timer timer;

		public OverTimer(Seat seat, User user) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					if (seat.getIdle() == 2) {
						seat.setIdle(0);
						seat.setUserNumber(null);
						user.setSeatId(0);
						seatDao.save(seat);
						userDao.save(user);
						System.out.println("已更新状态");
					}
				}
			}, 20 * 60 * 1000);
		}
	}
}
