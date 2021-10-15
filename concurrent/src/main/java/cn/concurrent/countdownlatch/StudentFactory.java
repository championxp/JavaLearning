package cn.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

public class StudentFactory {

    public static Student initStudent(String name, CountDownLatch countDownLatch, Long arriveTime) {
        return Student.builder().name(name).countDownLatch(countDownLatch).arriveTime(arriveTime).build();
    }
}
