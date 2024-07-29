package com.example.web;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.example.feign.FeignTest;
import com.example.pojo.Order;
import com.example.pojo.User;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${test111}")
    private String test;


    @GetMapping("/conf1")
    public String getConf1() {
        return test;
    }


    @GetMapping("/getID")
    public Order queryOrderByUserId() {
        // 根据id查询订单并返回
        return orderService.queryOrderById(101);
    }

    @GetMapping("/{id}")
    public User queryUserId(@PathVariable Integer id) {
        // 根据id查询订单并返回
        return orderService.getUser(id);
    }


    @GetMapping("/conf")
    public String getConf() {
        Config config = ConfigService.getAppConfig();
        String value = config.getProperty("test1",null);
        return value;
    }
}
