import java.util.ArrayList;

public class TreeMultiSet implements MultiSet {
    private final Tree tree;

    TreeMultiSet() {
        this.tree = new Tree(null, new ArrayList<>());
    }

    @Override
    public boolean add(Object item) {
        this.tree.insert(item);
        return true; // always returns True!
    }

    @Override
    public void remove(Object item) {
        this.tree.deleteItem(item);
    }

    @Override
    public boolean contains(Object item) {
        return this.tree.contains(item);
    }

    @Override
    public boolean is_empty() {
        return this.tree.isEmpty();
    }

    @Override
    public int count(Object item) {
        return this.tree.count(item);
    }

    @Override
    public int size() {
        return this.tree.length();
    }
}
