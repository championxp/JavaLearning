package cn.concurrent.cyclicbarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // parties 个任务全部到达 await 方法后，会执行该任务
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> System.out.println("裁判吹哨，打发令枪！"));
        List<Athlete> athletes = new ArrayList<>(10);
        athletes.add(AthleteFactory.initAthlete("张三", cyclicBarrier, 7000L));
        athletes.add(AthleteFactory.initAthlete("李四", cyclicBarrier, 2000L));
        athletes.add(AthleteFactory.initAthlete("王五", cyclicBarrier, 5000L));
        athletes.add(AthleteFactory.initAthlete("赵六", cyclicBarrier, 9000L));
        athletes.add(AthleteFactory.initAthlete("刘七", cyclicBarrier, 1000L));
        athletes.add(AthleteFactory.initAthlete("何八", cyclicBarrier, 6000L));
        athletes.add(AthleteFactory.initAthlete("苏九", cyclicBarrier, 3000L));
        athletes.add(AthleteFactory.initAthlete("陈十", cyclicBarrier, 8000L));
        athletes.add(AthleteFactory.initAthlete("王二", cyclicBarrier, 500L));
        athletes.add(AthleteFactory.initAthlete("小波", cyclicBarrier, 4000L));
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (Athlete athlete : athletes) {
            executor.execute(athlete);
        }
        executor.shutdown();
    }
}
