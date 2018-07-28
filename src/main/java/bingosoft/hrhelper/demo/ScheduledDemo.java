package bingosoft.hrhelper.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-07-27 19:01:01
 */
@Component
@Async//开启异步执行，可添加在类或者方法上
public class ScheduledDemo {

    /**
     * @Scheduled 以注解的方式指定定时任务执行规则
     * cron：指定cron表达式
     zone：默认使用服务器默认时区。可以设置为java.util.TimeZone中的zoneId
     fixedDelay：从上一个任务完成开始到下一个任务开始的间隔，单位毫秒
     fixedDelayString：同上，时间值是String类型
     fixedRate：从上一个任务开始到下一个任务开始的间隔，单位毫秒
     fixedRateString：同上，时间值是String类型
     initialDelay：任务首次执行延迟的时间，单位毫秒
     initialDelayString：同上，时间值是String类型
     */
    /*@Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
        String name = Thread.currentThread().getName();
        System.out.println("线程id：" + name);
    }
    @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
        String name = Thread.currentThread().getName();
        System.out.println("线程id：" + name);
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
        String name = Thread.currentThread().getName();
        System.out.println("线程id：" + name);
    }*/
}
