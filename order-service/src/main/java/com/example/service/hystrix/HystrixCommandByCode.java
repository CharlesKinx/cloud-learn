package com.example.service.hystrix;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;

import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;

/**
 * 使用编码的方式来实现服务容错
 *
 * Hystrix 舱壁模式
 * 1、线程隔离
 * 2、信号量
 */
@Slf4j
public class HystrixCommandByCode extends HystrixCommand<String> {


    /** 方法需要传递的参数 */
    private final String service;

    public HystrixCommandByCode(String serviceId) {
        /**
         * 使用线程池的方式
         */
        super(
                Setter.withGroupKey(
                                HystrixCommandGroupKey.Factory.asKey("ClientService"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("ClientHystrixCommand"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ClientPool"))
                        // 线程池 key 配置
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        .withExecutionIsolationStrategy(THREAD) // 线程池隔离策略
                                        .withFallbackEnabled(true)  // 开启降级
                                        .withCircuitBreakerEnabled(true)    // 开启熔断器
                        )
        );

        this.service = serviceId;
    }
        // 可以配置信号量隔离策略
//        Setter semaphore =
//                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("NacosClientService"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("NacosClientHystrixCommand"))
//                .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                        .withCircuitBreakerRequestVolumeThreshold(10)
//                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
//                        .withCircuitBreakerErrorThresholdPercentage(50)
//                        .withExecutionIsolationStrategy(SEMAPHORE)  // 指定使用信号量隔离
//                        //.....
//                );

    /**
     * 要保护调用的方法写在run方法中
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
        log.debug("使用Hystrix编码的方式保护服务...");
        return "SUCCESS";
    }

    @Override
    protected String getFallback() {

        log.debug("服务熔断....");
        return "FAIL";
    }
}
