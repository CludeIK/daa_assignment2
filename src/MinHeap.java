public class MinHeap {
    private int[] data;
    private int size;

    public long comparisons = 0;
    public long movements = 0;
    public long accesses = 0;

    public MinHeap() {
        this.data = new int[10];
        this.size = 0;
    }

    public void resetMetrics() {
        comparisons = 0;
        movements = 0;
        accesses = 0;
    }

    private void resize() {
        int[] newData = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
            movements++;
            accesses++;
        }
        data = newData;
    }

    public void insert(int x) {
        if (size == data.length) {
            resize();
        }
        data[size] = x;
        movements++;
        siftUp(size);
        size++;
    }

    public int peekMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        accesses++;
        return data[0];
    }

    public int extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");

        int min = data[0];
        accesses++;

        data[0] = data[size - 1];
        movements++;
        accesses++;
        size--;

        siftDown(0);

        return min;
    }

    private void siftUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0) {
            comparisons++;
            accesses += 2;
            if (data[index] >= data[parent]) {
                break;
            }
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private void siftDown(int index) {
        int minIndex = index;
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;

            if (leftChild < size) {
                comparisons++;
                accesses += 2;
                if (data[leftChild] < data[minIndex]) {
                    minIndex = leftChild;
                }
            }

            if (rightChild < size) {
                comparisons++;
                accesses += 2;
                if (data[rightChild] < data[minIndex]) {
                    minIndex = rightChild;
                }
            }

            if (minIndex == index) {
                break;
            }

            swap(index, minIndex);
            index = minIndex;
        }
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
        movements += 3;
        accesses += 4;
    }

    public int size() {
        return size;
    }
}