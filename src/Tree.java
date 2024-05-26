import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tree {
    // === Private Attributes ===
    // The item stored at this tree's root, or None if the tree is empty.
    private Object root;
    // The list of all subtrees of this tree.
    private final ArrayList<Tree> subtrees;

    Tree(Object root, ArrayList<Tree> subtrees) {
        this.root = root;

        // Create a copy of subtrees
        this.subtrees = new ArrayList<>(subtrees);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * @return Return the number of items contained in this tree.
     */
    public int length() {
        if (this.isEmpty()) {
            return 0;
        }
        else {
            // Count the root
            int size = 1;
            for (Tree subtree: this.subtrees) {
                size += subtree.length();
            }
            return size;
        }
    }

    public int count(Object item) {
        if (this.isEmpty()) {
            return 0;
        }
        else {
            int num = 0;
            if (this.root == item) {
                num += 1;
            }
            for (Tree subtree : this.subtrees) {
                num += subtree.count(item);
            }
            return num;
        }
    }

    public String toString() {
        // First version is commented out. This had the problem that it doesn't
        // distinguish between different levels in the tree, and just prints out
        // every item on a new line.
        // if this.isEmpty():
        //     return "";
        // else:
        //     String s = self._root.toString() + "\n";
        //     for (Tree subtree : this.subtrees) {
        //         s += subtree.toString();
        //     }
        //     return s;
        //
        // Instead, we call a recursive helper method.
        return this.strIndented(0);
    }

    private String strIndented(int depth) {
        if (this.isEmpty()) {
            return "";
        } else {
            StringBuilder s = new StringBuilder(" ".repeat(depth) + this.root.toString() + "\n");
            for (Tree subtree : this.subtrees) {
                // Note that the 'depth' argument to the recursive call is
                // modified.
                s.append(subtree.strIndented(depth + 1));
            }
            return s.toString();
        }
    }

    public float average() {
        if (this.isEmpty()) {
            return 0;
        }
        else {
            int[] average_helper_result = this.averageHelper();
            int total = average_helper_result[0];
            int count = average_helper_result[1];
            return (float) total / count;
        }
    }

    private int[] averageHelper() {
        if (this.isEmpty()) {
            return new int[2];
        }
        else {
            int total = (int) this.root;
            int size = 1;
            for (Tree subtree: this.subtrees) {
                int[] average_helper_result = subtree.averageHelper();
                int subtree_total = average_helper_result[0];
                int subtree_size = average_helper_result[1];

                total += subtree_total;
                size += subtree_size;
            }
            return new int[] {total,  size};
        }
    }

    public boolean equals(Tree other) {
        if (this.isEmpty() && other.isEmpty()) {
            return true;
        } else if (this.isEmpty() || other.isEmpty()) {
            return false;
        } else {
            if (this.root != other.root) {
                return false;
            }

            if (this.subtrees.size() != other.subtrees.size()) {
                return false;
            }

            return this.subtrees.equals(other.subtrees);
        }
    }

    public boolean contains(Object item) {
        if (this.isEmpty()) {
            return false;
        }

        // item may in root, or subtrees
        if (this.root == item) {
            return true;
        }

        else {
            for (Tree subtree: this.subtrees) {
                if (subtree.contains(item)) {
                    return true;
                }
            }
            return false;
        }
    }

    public ArrayList<Object> leaves() {
        if (this.isEmpty()) {
            return new ArrayList<>();
        } else if (this.subtrees.isEmpty()) {
            return new ArrayList<>(Collections.singletonList(this.root));
        } else {
            ArrayList<Object> leaves = new ArrayList<>();
            for (Tree subtree: this.subtrees) {
                leaves.addAll(subtree.leaves());
            }
            return leaves;
        }
    }

    // -------------------------------------------------------------------------
    // Mutating methods
    // -------------------------------------------------------------------------
    public boolean deleteItem(Object item) {
        if (this.isEmpty()) {
            // The item is not in the tree.
            return false;
        } else if (this.root == item) {
            // We've found the item: now delete it.
            this.deleteRoot();
            return true;
        } else {
            // Loop through each subtree, and stop the first time
            // the item is deleted. (This is why a boolean is returned!)
            for (Tree subtree: this.subtrees) {
                if (subtree.deleteItem(item)) {
                    if (subtree.isEmpty()) {
                        // The item was deleted and the subtree is now empty.
                        // We should remove the subtree from the list of subtrees.
                        // Note that mutate a list while looping through it is
                        // EXTREMELY DANGEROUS!
                        // We are only doing it because we return immediately
                        // afterward, and so no more loop iterations occur.
                        this.subtrees.remove(subtree);
                    }
                    else {
                        // The item was deleted, and the subtree is not empty.
                    }
                    return true;
                } else {
                    // No item was deleted. Continue onto the next subtree.
                    // Note that this branch is unnecessary; we've only shown
                    // it to write comments.
                }
            }
        }

        // If we don't return inside the loop, the item is not deleted
        // from any of the subtrees. In this case, the item does not
        // appear in this tree.
        return false;
    }

    public void deleteRoot() {
        if (this.subtrees.isEmpty()) {
            //  This is a leaf. Deleting the root gives and empty tree.
            this.root = null;
        } else {
            // Get the last subtree in this tree.
            Tree chosen_subtree = this.subtrees.removeLast();

            this.root = chosen_subtree.root;
            this.subtrees.addAll(chosen_subtree.subtrees);

            // Strategy 2: Replace with a leaf.
            // 1. Extract the leftmost leaf (using another helper).
            // Object leaf = this.extractLeaf();
            //
            // 2. Update this.root. (Note that this.subtrees remains the same.)
            // this.root = leaf;
        }
    }

    private Object extractLeaf() {
        if (this.subtrees.isEmpty()) {
            Object old_root = this.root;
            this.root = null;
            return old_root;
        } else {
            Object leaf = this.subtrees.getFirst().extractLeaf();
            // Need to check whether self._subtrees[0] is now empty,
            // and if so, remove it.
            if (this.subtrees.getFirst().isEmpty()) {
                this.subtrees.removeFirst();
            }

            return leaf;
        }
    }

    public void insert(Object item) {
        if (this.isEmpty()) {
            this.root = item;
        } else if (this.subtrees.isEmpty()) {
            this.subtrees.add(new Tree(item, new ArrayList<>()));
        } else {
            Random random = new Random();
            if ((random.nextInt(3) + 1) == 3) {
                this.subtrees.add(new Tree(item, new ArrayList<>()));
            } else {
                int subtree_index = random.nextInt(this.subtrees.size());
                this.subtrees.get(subtree_index).insert(item);
            }
        }
    }

    public boolean insertChild(Object item, Object parent) {
        if (this.isEmpty()) {
            return false;
        } else if (this.root == parent) {
            this.subtrees.add(new Tree(item, new ArrayList<>()));
            return true;
        } else {
            for (Tree subtree: this.subtrees) {
                if (subtree.insertChild(item, parent)) {
                    return true;
                }
            }
            return false;
        }
    }
}
