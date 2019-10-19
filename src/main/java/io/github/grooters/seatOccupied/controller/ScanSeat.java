package io.github.grooters.seatOccupied.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.grooters.seatOccupied.dao.SeatDao;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;
import java.util.Date;

@RestController
public class ScanSeat {
	@Autowired
	private SeatDao seatDao;
	@Autowired
	private UserDao userDao;
	private static final String SUCCESS = "1";
	//座位已被保护
	private static final String FAILED = "0";
	//座位不存在
	private static final String NONE = "-1";
	private static final String TIMEOUT = "2";
	private String number;
	private int seatId;
	public static String result;
	public static int count = 0;

	@RequestMapping(value = "/scanseat", method = RequestMethod.GET)
	// 1为已被占用，2为已被预约
	public String scanSeat(int seatid, String usernumber, int mins) {
		Seat seat = seatDao.findById(seatid);
		User user = userDao.findByNumber(usernumber);
		System.out.println("usernumber:"+usernumber);
		System.out.println("seatid:"+seatid);
		String leaveTime;
		if (seat != null) {
			System.out.println("seat != null");
			int idle=seat.getIdle();
			String userNumber=seat.getUserNumber();
			//要么该座位为空闲状态，要么是处于扫码者预约状态
			if(idle==0||(idle==2&&userNumber.equals(usernumber))){
				System.out.println("user:"+user);
				return initSeat(seat,user,usernumber,seatid,mins);
			}else if (idle == 3) {
				System.out.println("idle == 3");
				// 该座位已被保护
				return FAILED;

			}else if(seat.getIdle()==1){
				if(seat.getUserNumber().equals(usernumber)){
					return FAILED;
				}
				number=usernumber;
				seatId=seatid;
				System.out.println("usernumber:" + usernumber);
				WebConnecter.showDialog(usernumber, seatid);
				while (true) {
					System.out.println("while");
					if (result != null) {
						break;
					}
					//防止进入死循环
//					else if(t==50){
//						break;
//					}
//					t++;
				}
				if(result.equals("0")){
					System.out.println("result.equals(\"0\")");
					result=null;
					return "0";
				}else {
					System.out.println("else");
					//同意
					User userTemp=userDao.findByNumber(userNumber);
					userTemp.setSeatId(0);
					result=null;
					return initSeat(seat,user,usernumber,seatid,mins);
				}
			}
		}
		System.out.print("NONE");
		// 座位不存在
		return NONE;
	}

	private String initSeat(Seat seat,User user,String userNumber,int seatId,int mins){
		String leaveTime;
		//释放用户原有座位
		if (user.getSeatId()!=0) {
			Seat seatTemp = seatDao.findById(user.getSeatId());
			seatTemp.setIdle(0);
			seatTemp.setLeavetime("");
			seatTemp.setUserNumber("");
		}
		leaveTime = getLeavetime(mins);
		seat.setLeavetime(leaveTime);
		seat.setTime(mins);
		seat.setIdle(1);
		seat.setUserNumber(userNumber);
		seatDao.save(seat);
		user.setSeatId(seatId);
		user.setNum(user.getNum() + 1);
		userDao.save(user);
//		new OverTime(seat, user, mins);
		startSchedule(mins,user,seat);
		// 上座成功
		return JSON.toJSONString(user);
	}

	/**
	 * 当管理员对请求响应后由网页调用
	 */
	@RequestMapping(value = { "/updateSeat" }, method = RequestMethod.GET)
	public String updateSeat(String result) {
		ScanSeat.result = result;
		System.out.println("updateSeat:result:" + result);
		if (result.equals("1")) {
			System.out.println("number:" + number);
			User user=userDao.findByNumber(number);
			Seat seat=seatDao.findById(seatId);
			user.setSeatId(seatId);
			seat.setUserNumber(number);
			seat.setIdle(1);
			userDao.save(user);
			seatDao.save(seat);
		}
		return result;
	}

	private String getLeavetime(int mins) {
		Date date = new Date();
		date.setMinutes(date.getMinutes() + mins);
		System.out.println("date.getMinutes():"+date.getMinutes());
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String leavetime = format.format(date);
		return leavetime;
	}

	private void startSchedule(int minutes,User user,Seat seat){
		System.out.println("minutes:"+minutes);
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				user.setSeatId(0);
				seat.setLeavetime("");
				seat.setUserNumber("");
				seat.setIdle(0);
				userDao.save(user);
				seatDao.save(seat);
				System.out.println("end");
			}
		},minutes*60*1000);
	}

	private class OverTime {
		Timer timer;
		private OverTime(Seat seat, User user, int mins) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
//					if (count == 0) {
//						Date date1 = new Date();
//						try {
//							Date date2 = new SimpleDateFormat("HH:mm").parse(seat.getLeavetime());
//							int mins1 = (int) (date2.getTime() - date1.getTime()) / 1000 / 60;
//							if (mins1 > 0){
//								new OverTime(seat, user, mins1);
//								return;
//							}
//							else {
//								seat.setIdle(0);
//								seat.setUserNumber("");
//								seat.setTime(0);
//								seat.setLeavetime("");
//								user.setSeatId(0);
//								seatDao.save(seat);
//								userDao.save(user);
//							}
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					} else {
//						timer.cancel();
//					}

				}
			}, mins * 60 * 1000);
		}
		public String returnValue(){
			return TIMEOUT;
		}
	}
}