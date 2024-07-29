package com.example.service.controller;

import com.example.service.hystrix.HystrixCommandByCode;
import com.example.service.hystrix.UseHystrixCommandAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private UseHystrixCommandAnnotation commandAnnotation;

    /**
     * 使用注解的方式
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/test1")
    public String getTest() throws InterruptedException {
        return commandAnnotation.hystrixCommandAnnotationTest();
    }

    @GetMapping("/test2")
    public String getService() throws ExecutionException, InterruptedException {

        String service = "hello";
        //1、通过同步阻塞的方式
        String ans = new HystrixCommandByCode(service).execute();
        log.debug("使用同步阻塞的方式获取...[{}]",ans);

        //2、异步非阻塞
        Future<String> future = new HystrixCommandByCode(service).queue();
        //这里可以做一些别的事情....
        log.debug("使用异步非阻塞的方式获取...[{}]",future.get());

        //3、热响应调用
        Observable<String> observe = new HystrixCommandByCode(service).observe();
        // 当使用observe时，才会去调用
        String ans2 = observe.toBlocking().single();
        log.debug("使用热响应方式调用,[{}]",ans2);


        //4、异步冷响应调用，只用当调用toBlocking().single() 才会创建线程去执行run方法
        Observable<String> stringObservable = new HystrixCommandByCode(service).toObservable();
        String ans3 = stringObservable.toBlocking().single();
        log.debug("使用冷响应方式调用,[{}]",ans3);

        return ans;
    }
}
