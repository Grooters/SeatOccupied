package io.github.grooters.seatOccupied.model;

import java.util.List;

import javax.persistence.Entity;

public class AllSeat {
	private List<Seat> seats;

	public AllSeat(List<Seat> seats) {
		this.seats = seats;
	}

	public AllSeat() {
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public int getSize() {
		return seats.size();
	}

	public Seat get(int i) {
		return seats.get(i);
	}
}
