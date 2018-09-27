package io.github.grooters.seatOccupied.controller;

import com.alibaba.fastjson.JSON;
import io.github.grooters.seatOccupied.dao.UserDao;
import io.github.grooters.seatOccupied.model.Seat;
import io.github.grooters.seatOccupied.model.User;
import io.github.grooters.seatOccupied.tool.ZFCatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Login {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value={"/login"},method = RequestMethod.GET)
    public String getUserInfo(String number,String pass){
        User user=userDao.findByNumber(number);
        if(user==null){
            user=new User();
            ZFCatch zfCatch=new ZFCatch();
            int result=3;
            for(int i=0;i<10;i++){
                result=zfCatch.requestLogin(number,pass);
                //等于3说明验证码没有识别出来继续循环
                if(result!=3)
                    break;
            }
            switch (result){
                case 0:
                    //写到数据库
                    user=zfCatch.getPersonalInfo(user);
                    System.out.println("userName:"+user.getName());
                    userDao.save(user);
                    String json= JSON.toJSONString(user);
                    return json;
                case 1:
                    return "用户名不存在";
                case 2:
                    return  "密码错误";
                case 3:
                    return "服务器异常";
            }
        }else {
            if(!pass.equals(user.getPass())){
                return "密码错误";
            }
            //直接从数据库中拿
            return JSON.toJSONString(user);
        }
        return "服务器异常";
    }

    //test
    @RequestMapping(value = {"/list"},method = RequestMethod.GET)
    public String list(){
        Seat s1=new Seat(1,1,1,"1",1,1);
        Seat s2=new Seat(2,2,2,"2",2,2);
        List<Seat> seats=new ArrayList<>();
        seats.add(s1);
        seats.add(s2);
        return JSON.toJSONString(seats);
    }


}
