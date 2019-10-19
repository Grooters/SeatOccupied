package io.github.grooters.seatOccupied.controller;

import java.util.ArrayList;
import java.util.List;

import io.github.grooters.seatOccupied.tool.CheckCoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.github.grooters.seatOccupied.dao.SeatDao;
import io.github.grooters.seatOccupied.model.AllSeat;
import io.github.grooters.seatOccupied.model.Seat;

@RestController
public class SeatMsg {

	@Autowired
	private SeatDao seatDao;

	@RequestMapping(value = { "/allseat" }, method = RequestMethod.GET)
	public String getSeat() {
		AllSeat seats = new AllSeat();
		seats.setSeats(seatDao.findAll());
		if (seats != null) {
			List<Seat> sortedSeats = new ArrayList<Seat>();
			for (int i = 1; i < 5; i++) {
				for (int j = 0; j < seats.getSize(); j++) {
					if (seats.get(j).getFloor() == i) {
						sortedSeats.add(seats.get(j));
					}
				}
			}
			seats.setSeats(sortedSeats);
			return JSON.toJSONString(seats);
		} else
			return "服务器异常";
	}

}