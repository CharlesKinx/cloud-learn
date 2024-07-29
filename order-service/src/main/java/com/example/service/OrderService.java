package com.example.service;

import com.example.feign.FeignTest;
import com.example.mapper.OrderMapper;
import com.example.pojo.Order;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;



//    public Order queryOrderById(Long orderId) {
//        // 1.查询订单
//        Order order = orderMapper.findById(orderId);
//        // 2.用Feign远程调用
//        User user = userClient.findById(order.getUserId());
//        // 3.封装user到Order
//        order.setUser(user);
//        // 4.返回
//        return order;
//    }

    @Autowired
    private RestTemplate restTemplate;

    private final FeignTest feignTest;

    public OrderService(FeignTest feignTest) {
        this.feignTest = feignTest;
    }

    @Autowired
    private DiscoveryClient discoveryClient;
    public Order queryOrderById(Integer orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        getInstance();

        // 2.利用RestTemplate发起http请求，查询用户
        // 2.1.url路径
        System.out.println(order);
        String url = "http://user-service/user/" + order.getUserId();
        // 2.2.发送http请求，实现远程调用
        User user = restTemplate.getForObject(url, User.class);
        // 3.封装user到Order
        order.setUser(user);
        // 4.返回
        return order;
    }

    public User getUser(Integer id){
        return this.feignTest.queryById(id);
    }

    private void getInstance() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("user-service");

        if(serviceInstances == null) {
            System.out.println("服务列表为空...");
            return;
        }

        for(ServiceInstance instance : serviceInstances) {

            System.out.println("获取服务地址"+instance.getUri()+":"+instance.getPort());
            System.out.println("权重为："+instance.getMetadata().get("weight"));
            System.out.println("地区为："+instance.getMetadata().get("area"));
        }
    }
}
