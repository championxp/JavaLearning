package cn.concurrent.countdownlatch;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

@Data
@Builder
public class Student implements Runnable{

    private String name;
    private CountDownLatch countDownLatch;
    private Long arriveTime;

    public void start() {
        System.out.println(name + "从家里出发。");
    }

    public void arrive() {
        System.out.println((arriveTime / 1000) + "分钟后，" + name + "到达春游集合点。");
    }

    public void active() {
        System.out.println(name + "在集体上车前自由活动");
    }

    @SneakyThrows
    @Override
    public void run() {
        start();
        Thread.sleep(arriveTime);
        arrive();
        // 倒计数减 1 ，但会继续执行线程中剩下任务
        countDownLatch.countDown();
        active();
    }
}
