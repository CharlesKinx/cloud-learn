package com.example.feign;


import com.example.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface FeignTest {

    @GetMapping("/user/{id}")
    User queryById(@PathVariable("id") Integer id);
}
