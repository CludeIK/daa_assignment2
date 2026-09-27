public class LinkedList {
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public long accesses = 0;
    public long comparisons = 0;
    public long movements = 0;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void resetMetrics() {
        accesses = 0;
        comparisons = 0;
        movements = 0;
    }

    private Node getNode(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
            accesses++;
        }
        return current;
    }

    public void add(int x) {
        Node newNode = new Node(x);
        movements++;
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
                accesses++;
            }
            current.next = newNode;
            movements++;
        }
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Invalid index");

        if (index == 0) {
            Node newNode = new Node(x);
            newNode.next = head;
            head = newNode;
            movements += 2;
        } else {
            Node prev = getNode(index - 1);
            Node newNode = new Node(x);
            newNode.next = prev.next;
            prev.next = newNode;
            movements += 3;
        }
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid index");

        if (index == 0) {
            head = head.next;
            movements++;
        } else {
            Node prev = getNode(index - 1);
            prev.next = prev.next.next;
            movements++;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid index");
        accesses++;
        return getNode(index).data;
    }

    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            accesses++;
            comparisons++;
            if (current.data == x) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }
}