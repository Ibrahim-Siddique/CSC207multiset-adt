public class LinkedListMultiSet implements MultiSet {
    private Node front;
    private int size;

    LinkedListMultiSet() {
        this.front = null;
        this.size = 0;
    }

    @Override
    public boolean add(Object item) {
        Node new_node = new Node(item, null);
        new_node.next = this.front;
        this.front = new_node;
        this.size++;
        return true;
    }

    @Override
    public void remove(Object item) {
        Node curr = this.front;
        Node prev = null;
        while (curr != null) {
            if (curr.equals(item)) {
                this.size--;
                if (prev != null) {
                    prev.next = curr.next;
                } else {
                    this.front = curr.next;
                }
                return;
            }
            prev = curr;
            curr = curr.next;
        }
        // if here, item not found
    }

    @Override
    public boolean contains(Object item) {
        Node curr = this.front;
        while (curr.next != null) {
            if (curr.equals(item)) {
                return true;
            }
            curr = curr.next;
        }
        return curr.equals(item);
    }

    @Override
    public boolean is_empty() {
        return this.front == null;
    }

    @Override
    public int count(Object item) {
        int num_seen = 0;
        Node curr = this.front;
        while (curr != null) {
            if (curr.equals(item)) {
                num_seen++;
            }
            curr = curr.next;
        }
        return num_seen;
    }

    @Override
    public int size() {
        return this.size;
    }
}
