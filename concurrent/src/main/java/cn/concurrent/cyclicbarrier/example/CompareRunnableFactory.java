package cn.concurrent.cyclicbarrier.example;

import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class CompareRunnableFactory {

    public static CompareRunnable initCompareRunnable(String type, CyclicBarrier compBarrier, CyclicBarrier endBarrier, Map<String, Object> map) {
        return CompareRunnable.builder().type(type).compBarrier(compBarrier).endBarrier(endBarrier).map(map).build();
    }
}
