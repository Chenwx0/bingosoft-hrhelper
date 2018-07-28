package bingosoft.hrhelper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-07-27 19:14:14
 */
@Configuration
@EnableAsync
public class TaskAsyncConfig {

    @Value("${TaskAsync.corePoolSize}")
    private int corePoolSize;
    @Value("${TaskAsync.maxPoolSize}")
    private int maxPoolSize;
    @Value("${TaskAsync.queueCapacity}")
    private int queueCapacity;
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }
}

