import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class PQTest {

    public static Integer[] nums = {};
    public static void main(String[] args) {

        PriorityQueue<Integer> pq1 = new PriorityQueue<>();

        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            pq1.add(random.nextInt(100));
        }

        // 从小到大输出，是小根堆
        while (!pq1.isEmpty()) {
            System.out.print(pq1.poll() + " ");
        }

        System.out.println();

        // 自定义一个大根堆
        PriorityQueue<Integer> pq2 = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // 这里把比较顺序反过来
                return o2.compareTo(o1);
            }
        });
        for (int i = 0; i < 30; i++) {
            pq2.add(random.nextInt(100));
        }

        // 从小到大输出，是小根堆
        while (!pq2.isEmpty()) {
            System.out.print(pq2.poll() + " ");
        }

        System.out.println();

    }
}
