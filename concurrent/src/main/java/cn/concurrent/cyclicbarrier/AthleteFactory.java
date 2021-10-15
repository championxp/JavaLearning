package cn.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class AthleteFactory {

    public static Athlete initAthlete(String name, CyclicBarrier cyclicBarrier, Long arriveTime) {
        return Athlete.builder().name(name).cyclicBarrier(cyclicBarrier).arriveTime(arriveTime).build();
    }
}
