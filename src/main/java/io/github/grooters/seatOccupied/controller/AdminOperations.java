package io.github.grooters.seatOccupied.controller;

import io.github.grooters.seatOccupied.tool.CheckCoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.github.grooters.seatOccupied.dao.*;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;

@RestController
public class AdminOperations {
	@Autowired
	private SeatDao seatDao;
	@Autowired
	private UserDao userDao;
	private String number;
	private int id;
	private String result;

	@RequestMapping(value = { "/addseat" }, method = RequestMethod.GET)
	public String addSeat(int floor, int number) {
		String userNumber = "";
		int time = 0;
		String leaveTime = "";
		int idle = 0;
		List<Seat> seat = seatDao.findAll();
		if (seat.size() < 1) {
			Seat seat3 = new Seat(floor, number, idle, userNumber, leaveTime, time);
			seatDao.save(seat3);
			CheckCoder.createCode("1");
			return "1";
		}
		for (int i = 0; i < seat.size(); i++) {
			Seat seat1 = seat.get(i);
			if (floor > 5)
				return "0";
			if (seat1.getFloor() == floor && seat1.getNumber() == number)
				return "0";
			else if (i == seat.size() - 1) {
				Seat seat2 = new Seat(floor, number, idle, userNumber, leaveTime, time);
				CheckCoder.createCode(String.valueOf(i+2));
				seatDao.save(seat2);
				return "1";
			}
		}
		return "-1";
	}

	@RequestMapping(value = { "/changestate" }, method = RequestMethod.GET)
	public String changeState(int seatidle, int seatid, String usernumber1, int mins) {
		ScanSeat scanseat = new ScanSeat();
		;
		User user1 = userDao.findByNumber(usernumber1);// 后来的上座者
		Seat seat = seatDao.findById(seatid);
		User user2 = userDao.findByNumber(seat.getUserNumber());// 前面的上座者
		if (seat != null) {
			switch (seatidle) {
			case 0:
				user1.setSeatId(0);
				seat.setUserNumber(null);
				seat.setIdle(0);
				return "1";
			case 1:
				user2.setSeatId(0);
				seat.setUserNumber(null);
				seat.setIdle(0);
				scanseat.scanSeat(seatid, usernumber1, mins);
				return "1";
			}
		}
		return "-1";
	}

	@RequestMapping(value = { "/createCode" }, method = RequestMethod.GET)
	public String createCode(String id) {
		CheckCoder.createCode(id);
		return id;
	}

	@RequestMapping(value = { "/requestUpdateSeat" }, method = RequestMethod.GET)
	public String requestUpdateSeat(String number, int id) {
		WebConnecter.showDialog(number, id);
		this.number = number;
        System.out.println("requestUpdateSeat-number:" + number);
		this.id = id;
		while (true) {
			System.out.println("result:" + result);
			if (result != null) {
				break;
			}
		}
		System.out.println("Thread_result:" + result);
		return result;
	}


	@RequestMapping(value = { "/createCodes" }, method = RequestMethod.GET)
	public void deleteSeat() {
	    List<Seat> seats=new ArrayList<>();
	    seats=seatDao.findAll();
	    for(int i=1;i<seats.size()+1;i++){
	        CheckCoder.createCode(String.valueOf(i));
        }
	}

}
