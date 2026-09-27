public class DynamicArray {
    private int[] data;
    private int size;

    public long accesses = 0;
    public long comparisons = 0;
    public long movements = 0;

    public DynamicArray() {
        this.data = new int[10];
        this.size = 0;
    }

    public void resetMetrics() {
        accesses = 0;
        comparisons = 0;
        movements = 0;
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

    public void add(int x) {
        if (size == data.length) {
            resize();
        }
        data[size] = x;
        size++;
        movements++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Invalid index");
        if (size == data.length) {
            resize();
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            movements++;
            accesses += 2;
        }
        data[index] = x;
        size++;
        movements++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid index");

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            movements++;
            accesses += 2;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid index");
        accesses++;
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            accesses++;
            comparisons++;
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }
}
