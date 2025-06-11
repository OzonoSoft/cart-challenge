package ar.com.gabriel.cart.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("checkoutExecutor")
    public Executor checkoutExecutor() {
        ThreadPoolTaskExecutor exe = new ThreadPoolTaskExecutor();
        exe.setCorePoolSize(4);
        exe.setMaxPoolSize(8);
        exe.setQueueCapacity(100);
        exe.setThreadNamePrefix("checkout-");
        exe.initialize();
        return exe;
    }
}