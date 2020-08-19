package com.shareditor.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_OK, id: " + id + "\t" + "O(∩_∩)O哈哈~";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_Timeout_handler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String paymentInfo_Timeout(Integer id) {
        int tmp = 10 / 0;
        int timeSec = 5;
        try {
            TimeUnit.SECONDS.sleep(timeSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Timeout, id: " + id + "\t" + "o(╥﹏╥)o";
    }

    public String paymentInfo_Timeout_handler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_Timeout_handler 被降级";
    }
}
