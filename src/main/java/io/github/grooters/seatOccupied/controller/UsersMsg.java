package io.github.grooters.seatOccupied.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.AllUsers;
import io.github.grooters.seatOccupied.model.User;

import java.util.List;

@RestController
public class UsersMsg {
	@Autowired
	UserDao userDao;

	private static final String SUCCESS="1";
	private static final String FAILURE="0";

	@RequestMapping(value = { "allusers" }, method = RequestMethod.GET)
	public String getAllUsers() {
		AllUsers users = new AllUsers();
		users.setUsers(userDao.findAll());
		for (int i = 0; i < users.getSize(); i++) {
			User u = (User) users.get(i);
			// 将List里面的User对象中的department与major拼起来，major置空
			u.setDepartment(u.getDepartment() + u.getMajor());
			u.setMajor("");
			users.set(i, u);
		}
		return JSON.toJSONString(users);
	}

	@RequestMapping(value = {"editNickName"},method = RequestMethod.GET)
	public String editNickName(String number,String nickName){
		User user=userDao.findByNumber(number);
		if(user!=null){
			user.setNickname(nickName);
			userDao.save(user);
			return JSON.toJSONString(user);
		}
		return FAILURE;
	}

	@RequestMapping(value = {"editSignature"},method = RequestMethod.GET)
	public String editSignature(String number,String signature){
		User user=userDao.findByNumber(number);
		if(user!=null){
			user.setSignature(signature);
			userDao.save(user);
			return JSON.toJSONString(user);
		}
		return FAILURE;
	}

}
