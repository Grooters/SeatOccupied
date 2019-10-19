package io.github.grooters.seatOccupied.dao;

import org.springframework.data.repository.CrudRepository;

import io.github.grooters.seatOccupied.model.Seat;

import java.util.List;

public interface SeatDao extends CrudRepository<Seat, Integer> {
	// 通过座位号查找
	public Seat findByNumber(int number);

	// 通过座位ID查找
	public Seat findById(int id);

	public List<Seat> findAll();
}
