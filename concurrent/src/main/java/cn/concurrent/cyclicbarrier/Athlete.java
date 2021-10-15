package cn.concurrent.cyclicbarrier;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

@Data
@Builder
public class Athlete implements Runnable{

    private String name;
    private CyclicBarrier cyclicBarrier;
    private Long arriveTime;

    public void start() {
        System.out.println(name + "到达起跑线开始准备。");
    }

    public void ready() {
        System.out.println((arriveTime / 2000) + "秒后，" + name + "准备就绪。");
    }

    public void running() {
        System.out.println(name + "起跑、加速、冲刺、完成比赛。");
    }

    @SneakyThrows
    @Override
    public void run() {
        start();
        Thread.sleep(arriveTime);
        ready();
        // 等待，直到初始化 CyclicBarrier 时的 parties 个任务到达，然后所有的等待结束，一起继续执行
        cyclicBarrier.await();
        running();
    }
}
