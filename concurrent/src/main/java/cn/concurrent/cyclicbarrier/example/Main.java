package cn.concurrent.cyclicbarrier.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    /**
     * 需求场景：
     *      异构数据库间数据比对需求
     *      DB2 端数据库为 GBK 编码，Oracle 目标数据库为 UTF8 编码
     *      由于 DB2 自身对于 GBK 编码的优化，导致库本身的 Sequence 排序不完全一致
     *      区别为： A < B < ... < a < b 和 a < A < b < B < ...
     *      因此无法对主键分段后进行数据比对，只能查询全表数据统一比对
     *      应用内存是有限的,需要循环查询一定数量的数据进行比对
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        System.out.println("获取源端数据库连接，并获取 ResultSet");
        System.out.println("获取目标数据库连接，并获取 ResultSet");
        Map<String, Object> srcMap = new HashMap<>(1000);
        Map<String, Object> tgtMap = new HashMap<>(1000);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CyclicBarrier compBarrier = new CyclicBarrier(2, () -> {
            System.out.println("对源与目标的数据进行比对，未匹配的数据放入 redis");
        });
        CyclicBarrier endBarrier = new CyclicBarrier(2, () -> {
            System.out.println("处理所有未匹配的数据，进行结算");
            countDownLatch.countDown();
        });
        CompareRunnable srcRunnable = CompareRunnableFactory.initCompareRunnable("源端", compBarrier, endBarrier, srcMap);
        CompareRunnable tgtRunnable = CompareRunnableFactory.initCompareRunnable("目标", compBarrier, endBarrier, tgtMap);
        Future<?> srcFuture = executor.submit(srcRunnable);
        Future<?> tgtFuture = executor.submit(tgtRunnable);
        countDownLatch.await();
        closeResource(executor, srcFuture, tgtFuture);
    }

    private static void closeResource(ThreadPoolExecutor executor, Future<?> srcFuture, Future<?> tgtFuture) {
        System.out.println("关闭所有资源");
        srcFuture.cancel(true);
        tgtFuture.cancel(true);
        executor.shutdown();
    }
}
