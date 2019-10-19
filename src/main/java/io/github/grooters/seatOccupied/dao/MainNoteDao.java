package io.github.grooters.seatOccupied.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.github.grooters.seatOccupied.model.MainNote;

public interface MainNoteDao extends CrudRepository<MainNote, Integer> {
	public MainNote findById(int id);

	public MainNote findByTitle(String title);

	public MainNote findByUserName(String UserName);

	public MainNote findByUserNumber(String userNumber);

	public List<MainNote> findByDate(String date);

	public List<MainNote> findAll();
}