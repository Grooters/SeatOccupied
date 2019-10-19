package io.github.grooters.seatOccupied.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.github.grooters.seatOccupied.model.ChildNote;

public interface ChildNoteDao extends CrudRepository<ChildNote, Integer> {
	public ChildNote findById(int id);

	public ChildNote findByUserName(String userName);


	public List<ChildNote> findByMainPostId(int MainPostId);

	public List<ChildNote> findByDate(String Date);
}
