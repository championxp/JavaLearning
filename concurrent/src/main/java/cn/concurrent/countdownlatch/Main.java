package cn.concurrent.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        List<Student> students = new ArrayList<>(10);
        students.add(StudentFactory.initStudent("张三", countDownLatch, 7000L));
        students.add(StudentFactory.initStudent("李四", countDownLatch, 2000L));
        students.add(StudentFactory.initStudent("王五", countDownLatch, 5000L));
        students.add(StudentFactory.initStudent("赵六", countDownLatch, 9000L));
        students.add(StudentFactory.initStudent("刘七", countDownLatch, 1000L));
        students.add(StudentFactory.initStudent("何八", countDownLatch, 6000L));
        students.add(StudentFactory.initStudent("苏九", countDownLatch, 3000L));
        students.add(StudentFactory.initStudent("陈十", countDownLatch, 8000L));
        students.add(StudentFactory.initStudent("王二", countDownLatch, 500L));
        students.add(StudentFactory.initStudent("小波", countDownLatch, 4000L));
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (Student student : students) {
            executor.execute(student);
        }
        countDownLatch.await();
        System.out.println("全员到齐，出发！");
        executor.shutdown();
    }
}
