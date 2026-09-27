import java.util.PriorityQueue;
import java.util.Random;

public class Tests {
    public static void main(String[] args) {
        System.out.println("Starting Tests...\n");
        testDynamicArray();
        testLinkedList();
        testMinHeap();
        System.out.println("\nALL TESTS PASSED SUCCESSFULLY! ✅");
    }

    private static void testDynamicArray() {
        System.out.print("Testing Dynamic Array... ");
        DynamicArray arr = new DynamicArray();

        assertException(() -> arr.get(0), "Empty get");
        assertException(() -> arr.remove(0), "Empty remove");

        arr.add(10);
        if (arr.get(0) != 10) throw new RuntimeException("Add/Get failed");

        arr.add(10);
        arr.add(20);
        if (!arr.contains(10) || !arr.contains(20)) throw new RuntimeException("Contains failed");

        arr.add(1, 15); // [10, 15, 10, 20]
        if (arr.get(1) != 15) throw new RuntimeException("Insert at index failed");

        assertException(() -> arr.get(-1), "Negative index");
        assertException(() -> arr.get(100), "Out of bounds index");

        arr.remove(1);
        if (arr.get(1) != 10) throw new RuntimeException("Remove failed");

        System.out.println("OK");
    }

    private static void testLinkedList() {
        System.out.print("Testing Linked List... ");
        LinkedList list = new LinkedList();

        assertException(() -> list.get(0), "Empty get");

        list.add(5);
        if (list.get(0) != 5) throw new RuntimeException("Add/Get failed");

        list.add(0, 1);
        list.add(2, 10);
        if (list.get(0) != 1 || list.get(2) != 10) throw new RuntimeException("Insert bounds failed");

        list.remove(0);
        if (list.get(0) != 5) throw new RuntimeException("Remove head failed");

        if (!list.contains(10)) throw new RuntimeException("Contains failed");

        System.out.println("OK");
    }

    private static void testMinHeap() {
        System.out.print("Testing Min-Heap... ");
        MinHeap heap = new MinHeap();
        PriorityQueue<Integer> javaHeap = new PriorityQueue<>();
        Random rand = new Random(42);

        assertException(heap::peekMin, "Empty peek");
        assertException(heap::extractMin, "Empty extract");

        for (int i = 0; i < 1000; i++) {
            int val = rand.nextInt(500);
            heap.insert(val);
            javaHeap.add(val);
        }

        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < 1000; i++) {
            int myMin = heap.extractMin();
            int javaMin = javaHeap.poll();

            if (myMin != javaMin) throw new RuntimeException("Mismatch with Java PriorityQueue");

            if (myMin < prev) throw new RuntimeException("Heap property violated!");
            prev = myMin;
        }

        System.out.println("OK");
    }

    private static void assertException(Runnable action, String msg) {
        try {
            action.run();
            throw new RuntimeException("Expected exception did not occur: " + msg);
        } catch (IndexOutOfBoundsException | IllegalStateException e) { }
    }
}