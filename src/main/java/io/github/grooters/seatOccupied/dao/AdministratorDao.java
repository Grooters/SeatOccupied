package io.github.grooters.seatOccupied.dao;

import io.github.grooters.seatOccupied.model.Administrator;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface AdministratorDao extends CrudRepository<Administrator, Integer> {
	public List<Administrator> findByName(String name);

	public Administrator findByNumber(String number);

	public List<Administrator> findAll();
}
