package com.example.service;


import com.example.mapper.UserMapper;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Integer id) {
        System.out.println("我被访问了");
        return userMapper.findById(id);
    }
}