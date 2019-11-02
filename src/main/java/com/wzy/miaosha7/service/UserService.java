package com.wzy.miaosha7.service;

import com.wzy.miaosha7.dao.UserDao;
import com.wzy.miaosha7.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public User getById(int id) {
        User user= (User) redisTemplate.opsForValue().get("user" + String.valueOf(id));
        if(user != null) {
            return user;
        }else {
            User newUser = userDao.getById(id);
            redisTemplate.opsForValue().set("user"+ String.valueOf(id),newUser);
            return newUser;
        }
//        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(3);
        u1.setUser("zaq");
        return true;
    }
}
