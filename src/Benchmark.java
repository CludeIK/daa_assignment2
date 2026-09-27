import java.util.Random;

public class Benchmark {
    private static final int[] N_VALUES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;
    private static final long SEED = 42;

    public static void main(String[] args) {
        System.out.println("Starting Benchmarks for Assignment 2...\n");
        runWorkload1();
        runWorkload2();
        runWorkload3();
        runWorkload4();
    }

    private static void runWorkload1() {
        System.out.println("=== Workload 1: Random Access ===");
        System.out.printf("%-8s | %-15s | %-15s | %-15s | %-15s\n", "N", "Array Time(ns)", "Array Accesses", "List Time(ns)", "List Accesses");

        for (int n : N_VALUES) {
            long totalArrayTime = 0, totalListTime = 0;
            long arrayAccesses = 0, listAccesses = 0;

            for (int run = 0; run < RUNS; run++) {
                Random rand = new Random(SEED + run);
                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();

                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    array.add(val);
                    list.add(val);
                }

                int m = 10000;
                int[] indices = new int[m];
                for (int i = 0; i < m; i++) indices[i] = rand.nextInt(n);

                array.resetMetrics();
                long start = System.nanoTime();
                for (int i = 0; i < m; i++) array.get(indices[i]);
                totalArrayTime += (System.nanoTime() - start);
                arrayAccesses = array.accesses;

                list.resetMetrics();
                start = System.nanoTime();
                for (int i = 0; i < m; i++) list.get(indices[i]);
                totalListTime += (System.nanoTime() - start);
                listAccesses = list.accesses;
            }
            System.out.printf("%-8d | %-15d | %-15d | %-15d | %-15d\n",
                    n, totalArrayTime / RUNS, arrayAccesses, totalListTime / RUNS, listAccesses);
        }
        System.out.println();
    }

    private static void runWorkload2() {
        System.out.println("=== Workload 2: Search ===");
        System.out.printf("%-8s | %-15s | %-15s | %-15s | %-15s\n", "N", "Array Time(ns)", "Array Comps", "List Time(ns)", "List Comps");

        for (int n : N_VALUES) {
            long totalArrayTime = 0, totalListTime = 0;
            long arrayComps = 0, listComps = 0;

            for (int run = 0; run < RUNS; run++) {
                Random rand = new Random(SEED + run);
                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();

                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt(n * 2);
                    array.add(val);
                    list.add(val);
                }

                int m = 1000;
                int[] searchValues = new int[m];
                for (int i = 0; i < m; i++) searchValues[i] = rand.nextInt(n * 2);

                array.resetMetrics();
                long start = System.nanoTime();
                for (int i = 0; i < m; i++) array.contains(searchValues[i]);
                totalArrayTime += (System.nanoTime() - start);
                arrayComps = array.comparisons;

                list.resetMetrics();
                start = System.nanoTime();
                for (int i = 0; i < m; i++) list.contains(searchValues[i]);
                totalListTime += (System.nanoTime() - start);
                listComps = list.comparisons;
            }
            System.out.printf("%-8d | %-15d | %-15d | %-15d | %-15d\n",
                    n, totalArrayTime / RUNS, arrayComps, totalListTime / RUNS, listComps);
        }
        System.out.println();
    }

    private static void runWorkload3() {
        System.out.println("=== Workload 3: Insertion and Removal ===");
        int m = 1000;

        for (int n : N_VALUES) {
            System.out.println("N = " + n);
            long timeArrIns0 = 0, timeListIns0 = 0, timeArrRem0 = 0, timeListRem0 = 0;
            long timeArrInsM = 0, timeListInsM = 0, timeArrRemM = 0, timeListRemM = 0;

            for (int run = 0; run < RUNS; run++) {
                Random rand = new Random(SEED + run);

                DynamicArray arr0 = new DynamicArray();
                LinkedList list0 = new LinkedList();
                for (int i = 0; i < n; i++) {
                    arr0.add(i); list0.add(i);
                }

                long start = System.nanoTime();
                for (int i = 0; i < m; i++) arr0.add(0, -1);
                timeArrIns0 += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < m; i++) list0.add(0, -1);
                timeListIns0 += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < m; i++) arr0.remove(0);
                timeArrRem0 += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < m; i++) list0.remove(0);
                timeListRem0 += (System.nanoTime() - start);

                DynamicArray arrM = new DynamicArray();
                LinkedList listM = new LinkedList();
                for (int i = 0; i < n; i++) {
                    arrM.add(i); listM.add(i);
                }

                start = System.nanoTime();
                for (int i = 0; i < m; i++) arrM.add(n / 2, -1);
                timeArrInsM += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < m; i++) listM.add(n / 2, -1);
                timeListInsM += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < m; i++) arrM.remove(n / 2);
                timeArrRemM += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < m; i++) listM.remove(n / 2);
                timeListRemM += (System.nanoTime() - start);
            }

            System.out.printf("  Insert Index 0   | Array: %-10d ns | List: %-10d ns\n", timeArrIns0/RUNS, timeListIns0/RUNS);
            System.out.printf("  Remove Index 0   | Array: %-10d ns | List: %-10d ns\n", timeArrRem0/RUNS, timeListRem0/RUNS);
            System.out.printf("  Insert Index n/2 | Array: %-10d ns | List: %-10d ns\n", timeArrInsM/RUNS, timeListInsM/RUNS);
            System.out.printf("  Remove Index n/2 | Array: %-10d ns | List: %-10d ns\n", timeArrRemM/RUNS, timeListRemM/RUNS);
        }
        System.out.println();
    }

    private static void runWorkload4() {
        System.out.println("=== Workload 4: Priority Processing (Min-Heap) ===");
        System.out.printf("%-8s | %-15s | %-15s | %-15s\n", "N", "Insert Time(ns)", "Extract Time(ns)", "Valid Order?");

        for (int n : N_VALUES) {
            long totalInsertTime = 0, totalExtractTime = 0;
            boolean isValidOrder = true;

            for (int run = 0; run < RUNS; run++) {
                Random rand = new Random(SEED + run);
                MinHeap heap = new MinHeap();
                int[] dataToInsert = new int[n];
                for (int i = 0; i < n; i++) dataToInsert[i] = rand.nextInt();

                long start = System.nanoTime();
                for (int i = 0; i < n; i++) heap.insert(dataToInsert[i]);
                totalInsertTime += (System.nanoTime() - start);

                int prev = Integer.MIN_VALUE;
                start = System.nanoTime();
                for (int i = 0; i < n; i++) {
                    int current = heap.extractMin();
                    if (current < prev) isValidOrder = false;
                    prev = current;
                }
                totalExtractTime += (System.nanoTime() - start);
            }
            System.out.printf("%-8d | %-15d | %-15d | %-15b\n",
                    n, totalInsertTime / RUNS, totalExtractTime / RUNS, isValidOrder);
        }
    }
}