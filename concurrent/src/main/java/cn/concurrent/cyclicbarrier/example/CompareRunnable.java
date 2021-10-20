package cn.concurrent.cyclicbarrier.example;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Data
@Builder
public class CompareRunnable implements Runnable {

    private String type;
    private CyclicBarrier compBarrier;
    private CyclicBarrier endBarrier;
    private Map<String, Object> map;
    private ResultSet resultSet;

    @SneakyThrows(value = SQLException.class)
    @Override
    public void run() {
        try {
            Integer rows = 0;
            while (resultSet.next()) {
                rows++;
                // 操作单行数据
                executeOperation();
                if (rows % 1000 == 0) {
                    try {
                        compBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 将剩余数据进行比对，再进行比对结算
            try {
                compBarrier.await();
                endBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // 异常需要执行结束
            try {
                endBarrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) {
                e.printStackTrace();
            }
        }
    }

    private void executeOperation() throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        System.out.println("do something...");
        map.put("XXX", "XXX");
    }
}
