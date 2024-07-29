package com.example.filter;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@Slf4j
@Component
@WebFilter(
        filterName = "HystrixRequestContextServletFilter",
        urlPatterns = "/*",
        asyncSupported = true
)
public class HystrixRequestContextServletFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        // 初始化 Hystrix 请求上下文
        // 在不同的 context 中缓存是不共享的
        // 这个初始化是必须的
        HystrixRequestContext requestContext = HystrixRequestContext.initializeContext();


    }

    /**
     * 配置 Hystrix 并发策略
     */
    public void hystrixConcurrentStrategyConfig() {

        try {

            HystrixConcurrencyStrategy target = HystrixConcurrencyStrategyDefault.getInstance();
            HystrixConcurrencyStrategy strategy = HystrixPlugins.getInstance().getConcurrencyStrategy();

            if(strategy instanceof HystrixConcurrencyStrategyDefault) {
                return;
            }

            // 将原来的并发策略保存下来


        }catch (Exception e) {
            log.error("注册策略失败...[{}]",e.getMessage(),e);
        }
    }
}
