package io.github.grooters.seatOccupied.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.grooters.seatOccupied.controller.OrderSeat.OverTimer;
import io.github.grooters.seatOccupied.dao.SeatDao;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;

@RestController
public class Pause {
	// 暂离成功
	public static final int SUCCESS = 1;
	// 操作有误，座位可能不是“占座”状态，不能暂离
	public static final int FAILED = 0;
	// 超时未归来
	public static final int TIMEOUT = -1;
	public static Date date1;
	// 暂离15分钟后的时间
	public static String PauseTime;
	public OverTime ot;
	@Autowired
	private SeatDao seatDao;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/pause", method = RequestMethod.GET)
	public int pauseSeat(int seatid) {
		System.out.println("seatId:"+seatid);
		Seat seat = seatDao.findById(seatid);
		// 座位状态是“占座”状态才能暂离
		if (seat.getIdle() == 1) {
			seat.setIdle(3);
			// 获取点击离座按键瞬间的时间
			date1 = new Date();
			Date date2;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String leavetime1 = seat.getLeavetime();
			try {
				date2 = format.parse(leavetime1);
				int h = (date2.getMinutes() + 15) / 60;
				date2.setHours(date2.getHours() + h);
				// String leavetime2 = getLeavetime(date2, 15);
				PauseTime = getLeavetime(date2, 15);
				// 更新离座时间
				// seat.setLeavetime(leavetime2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seatDao.save(seat);
			// 此处获取user仅仅是为了传参给OverTime的实例ot
			User user = userDao.findByNumber(seat.getUserNumber());
			ot = new OverTime(seat, user);
			return SUCCESS;
		} else {
			return FAILED;
		}
	}

	@RequestMapping(value = "back", method = RequestMethod.GET)
	public int backToSeat(int seatid) {
		Seat seat = seatDao.findById(seatid);
		// 座位是暂离状态才能取消暂离
		if (seat.getIdle() == 3) {
			// 获取点击“取消暂离”的瞬间时间
			Date date3 = new Date();
			// 计算出点击"取消暂离"和点击“暂离”两个事件的时间间隔
			long cost = date3.getTime() - date1.getTime();
			int costmins = (int) (cost / 1000 / 60);
			try {
				Date date4 = new SimpleDateFormat("HH:mm").parse(PauseTime);
				// 更新离座时间，，原来的离座时间加了暂离的15mins，但如今执行到这一步代表用户已在15mins内提前回来，所以要减去相应的时间，比如暂离了7分钟，leavetime就要减去(15-7)mins
				String leavetime3 = getLeavetime(date4, -(15 - costmins));
				seat.setIdle(1);
				seat.setLeavetime(leavetime3);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seatDao.save(seat);
			// 此时取消“离座超时”的计时器
			ot.count = 1;
			return SUCCESS;
		} else {
			return FAILED;
		}
	}

	public String getLeavetime(Date date, int addition) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		date.setMinutes(date.getMinutes() + addition);
		String leavetime = format.format(date);
		return leavetime;
	}

	// 离座超时
	class OverTime {
		Timer timer;
		// 用于判断是否中止“离座超时”的任务调度
		int count = 0;

		public OverTime(Seat seat, User user) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					if (count == 0) {
						// System.out.println("任务调度开始了");
						seat.setIdle(0);
						seat.setUserNumber(null);
						user.setSeatId(0);
						seatDao.save(seat);
						userDao.save(user);
						returnValue();
					} else {
						// System.out.println("任务调度取消了");
						timer.cancel();
					}
				}
			}, 15 * 60 * 1000);
		}

		public int returnValue() {
			return TIMEOUT;
		}
	}
}
