/*
 * 普通轮询算法
 */

import java.util.ArrayList;
import java.util.List;

public class RoundRobin {

    private static Integer index = 0;
    private static List<String> nodes = new ArrayList<>();
    // 記錄輪詢輸出結果
    public static StringBuffer stringBuffer = new StringBuffer();

    // 模擬數據
    static {
        nodes.add("192.168.1.101");
        nodes.add("192.168.1.102");
        nodes.add("192.168.1.103");
        System.out.println("普通輪詢算法的所有節點：" + nodes);
    }

    // 關鍵
    public String selectNode() {
        String ip = null;
        synchronized (RoundRobin.class) {
            if (index >= nodes.size()) {
                index = 0;
            }
            ip = nodes.get(index);
            stringBuffer.append(Thread.currentThread().getName()).append("==獲取節點: ").append(ip).append("\n");
            index++;
        }
        return ip;
    }

    // 并發測試 兩個綫程循環獲取節點
     public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            RoundRobin roundRobin1 = new RoundRobin();
            for (int i = 0; i < 21; i++) {
                roundRobin1.selectNode();
                try {
                    // 模擬業務處理耗時
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            RoundRobin roundRobin2 = new RoundRobin();
            for (int i = 0; i < 21; i++) {
                roundRobin2.selectNode();
                try {
                    // 模擬業務處理耗時
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 等待輪詢完成，統一輸出
         Thread.sleep(5000);
         // 虽然是不同线程访问，但ip地址还是按顺序分配
         System.out.println("=======================================");
         System.out.println(stringBuffer.toString());
     }
}