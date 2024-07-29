package com.example.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long price;
    private String name;
    private Integer num;

    @TableField("user_id")
    private Integer userId;
    private User user;
}