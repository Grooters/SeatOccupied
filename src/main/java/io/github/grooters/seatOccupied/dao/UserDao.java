package io.github.grooters.seatOccupied.dao;

import io.github.grooters.seatOccupied.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	// findBy固定，后面加实体字段即对应表的属性

	// public User findById(String id);

	public User findByNumber(String number);

	public List<User> findByName(String name);

	public User findByNickname(String nickname);

	public List<User> findAll();

}
