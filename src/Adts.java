import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Adts {
    public static void main(String[] args) {
        ArrayList<MultiSet> multisets = new ArrayList<MultiSet>(Arrays.asList(new TreeMultiSet(), new ArrayListMultiSet(), new LinkedListMultiSet()));

        for (MultiSet multiset : multisets) {
            for (int n : Arrays.asList(500, 1000, 2000, 4000)) {
                Adts.profileMultiSet(multiset, n);
            }
        }
    }

    public static void profileMultiSet(MultiSet my_input, int n) {
        // add n random items, then remove them all; we will only time the removal
        // step.
        ArrayList<Integer> items_added = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(101);
            my_input.add(x);
            items_added.add(x);
        }

        assert items_added.size() == n;

        long start = System.nanoTime();

        for (int x : items_added) {
            my_input.remove(x);
        };

        long end = System.nanoTime();

        assert items_added.isEmpty();

        // Calculate the elapsed time
        double elapsedTime = (end - start) / 1_000_000_000.0;

        // Print formatted output
        System.out.printf("%5d  %-37s  %.6f%n", n, my_input.getClass().getName(), elapsedTime);
    }
}
