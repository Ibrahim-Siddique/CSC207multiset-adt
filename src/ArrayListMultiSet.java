import java.util.ArrayList;
import java.util.Collections;

public class ArrayListMultiSet implements MultiSet {
    private ArrayList<Object> list;

    ArrayListMultiSet() {
        this.list = new ArrayList<>();
    }

    @Override
    public boolean add(Object item) {
        this.list.add(item);
        return true;
    }

    @Override
    public void remove(Object item) {
        if (this.list.contains(item)) {
            this.list.remove(item);
        }
    }

    @Override
    public boolean contains(Object item) {
        return this.list.contains(item);
    }

    @Override
    public boolean is_empty() {
        return this.list.isEmpty();
    }

    @Override
    public int count(Object item) {
        return Collections.frequency(this.list, item);
    }

    @Override
    public int size() {
        return this.list.size();
    }
}
