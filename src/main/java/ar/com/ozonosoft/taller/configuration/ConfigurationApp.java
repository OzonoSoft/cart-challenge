package ar.com.ozonosoft.taller.configuration;

import java.util.concurrent.Executor;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

/**
 * @author Gabriel Gonzalez
 */
@Configuration
@EnableAsync
@EnableJpaAuditing
public class ConfigurationApp {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean(name = "asyncCartApi")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncCartApiThread-");
        executor.initialize();
        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }

}
