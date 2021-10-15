package cn.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class StudentFactory {

    public static Student initStudent(String name, CountDownLatch countDownLatch, Long arriveTime) {
        return Student.builder().name(name).countDownLatch(countDownLatch).arriveTime(arriveTime).build();
    }
}
