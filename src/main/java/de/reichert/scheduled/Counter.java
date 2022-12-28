package de.reichert.scheduled;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Counter {

    private int i = 0;

    //@Scheduled(fixedRate = 1000,initialDelayString = "${initialDelay}")
    //@Scheduled(cron = "* 49-51 20 * * *")
    public void count() {
        System.out.println(i++);
    }

    @Scheduled(fixedRate = 1000)
    @Async
    public void multiple() throws InterruptedException {
        int j = i++;
        String name = Thread.currentThread().getName();
        System.out.println("start:" + j + " Threadname:" + name);
        Thread.currentThread().sleep(1500);
        System.out.println("end:" + j + " Threadname:" + name);
    }
}
