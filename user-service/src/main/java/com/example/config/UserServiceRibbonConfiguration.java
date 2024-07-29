package com.example.config;

import com.ribbonconf.RibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * 使用Java的方式来进行配置负载均衡
 */
@RibbonClient(name ="user-service",configuration = RibbonConfiguration.class)
@Configuration

/**
 * 全局配置
 */

//@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserServiceRibbonConfiguration {

}
