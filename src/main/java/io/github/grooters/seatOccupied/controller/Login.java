package io.github.grooters.seatOccupied.controller;

import com.alibaba.fastjson.JSON;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;
import io.github.grooters.seatOccupied.model.Administrator;
import io.github.grooters.seatOccupied.dao.AdministratorDao;
import io.github.grooters.seatOccupied.tool.ZFCatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Login {

	private static final int SUCCESS = 1;
	private static final int pass_error = 0;
	private static final int number_erro = -1;
	private static final int TIMEOUT = 2;

	@Autowired
	private UserDao userDao;
	@Autowired
	private AdministratorDao administratorDao;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String getUserInfo(String number, String pass) {
		User user = userDao.findByNumber(number);
		if (user == null) {
			user = new User();
			ZFCatch zfCatch = new ZFCatch();
			int result = 3;
			for (int i = 0; i < 10; i++) {
				result = zfCatch.requestLogin(number, pass);
				// 等于3说明验证码没有识别出来继续循环
				if (result != 3)
					break;
			}
			switch (result) {
			case 0:
				// 写到数据库
				user = zfCatch.getPersonalInfo(user);
				System.out.println("userName:" + user.getName());
				userDao.save(user);
				String json = JSON.toJSONString(user);
				return json;
			case 1:
				return "-1";
			case 2:
				return "0";
			case 3:
				return "2";
			}
		} else {
			if (!pass.equals(user.getPass())) {
				return "0";
			}
			// 直接从数据库中拿
			return JSON.toJSONString(user);
		}
		return "2";
	}

	@RequestMapping(value = { "/admlogin" }, method = RequestMethod.GET)
	public String administratorLogin(String number, String pass) {
		Administrator ad = administratorDao.findByNumber(number);
		System.out.print("number" + number);
		if (ad == null) {
			return "-1";
		} else {
			if (!pass.equals(ad.getPass()))
				return "0";
			return JSON.toJSONString(ad);
		}
	}


}
