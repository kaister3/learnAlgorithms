import java.sql.Array;
import java.util.*;

/**
 * 加权轮询(简单版)
 */
public class WeightedRoundRobin {

    private static Integer index = 0;

    private static Map<String, Integer> mapNodes = new HashMap<>();

    private static StringBuffer stringBuffer = new StringBuffer();

    static {
        mapNodes.put("192.168.1.101",1);
        mapNodes.put("192.168.1.102",3);
        mapNodes.put("192.168.1.103",2);
    }

    // 關鍵 二维数组降维
    public String selectNode() {
        List<String> nodes = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = mapNodes.entrySet().iterator();

        while (iterator.hasNext()) {
            var entry = iterator.next();
            String key = entry.getKey();
            for (int i = 0; i < entry.getValue(); i++) {
                nodes.add(key);
            }
        }

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
            WeightedRoundRobin weightedRoundRobin1 = new WeightedRoundRobin();
            for (int i = 0; i < 6; i++) {
                weightedRoundRobin1.selectNode();
                try {
                    // 模擬業務處理耗時
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            var weightedRoundRobin2 = new WeightedRoundRobin();
            for (int i = 0; i < 6; i++) {
                weightedRoundRobin2.selectNode();
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
