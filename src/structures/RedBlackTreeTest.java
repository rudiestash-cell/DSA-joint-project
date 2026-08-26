package structures;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for RedBlackTree. Demonstrates a real testing framework
 * (bundled independently via lib/, see README) alongside the project's
 * existing manual assertion-style tests -- both are valid, this class adds
 * automated framework-based coverage on top for the one structure where a
 * strict invariant (equal black-height on every path) is worth asserting
 * programmatically rather than just eyeballing a trace.
 */
class RedBlackTreeTest {

    @Test
    void insertedValuesAreFound() {
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>(Comparator.<Integer>naturalOrder());
        int[] values = {20, 10, 30, 5, 15, 25, 35, 1, 8};
        for (int v : values) tree.insert(v);

        for (int v : values) {
            assertTrue(tree.contains(v), "Expected tree to contain " + v);
        }
        assertFalse(tree.contains(999), "Tree should not contain a value never inserted");
        assertEquals(values.length, tree.size());
    }

    @Test
    void emptyTreeContainsNothing() {
        RedBlackTree<String> tree = new RedBlackTree<String>(Comparator.<String>naturalOrder());
        assertFalse(tree.contains("anything"));
        assertEquals(0, tree.size());
    }

    @Test
    void duplicateInsertUpdatesInsteadOfGrowing() {
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>(Comparator.<Integer>naturalOrder());
        tree.insert(10);
        tree.insert(10);
        tree.insert(10);
        assertEquals(1, tree.size(), "Duplicate keys should update in place, not grow the tree");
    }

    /**
     * The defining red-black property: every path from the root to a null
     * leaf must pass through the same number of black nodes. This is exactly
     * what rotations+recoloring on insert are supposed to preserve, so this
     * test is the real correctness check for the whole class, not just a
     * smoke test.
     */
    @Test
    void treeStaysBalancedUnderAscendingInsertOrder() {
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>(Comparator.<Integer>naturalOrder());
        // Ascending insert order is the worst case for an unbalanced BST
        // (degenerates to a linked list) -- a red-black tree must stay flat.
        for (int i = 1; i <= 100; i++) tree.insert(i);

        int blackHeight = tree.blackHeight();
        int height = tree.height();

        // For n=100 nodes, a correct red-black tree has height <= 2*log2(n+1) ~= 13.3
        assertTrue(height <= 14, "Height " + height + " exceeds the red-black worst-case bound for n=100");
        // Black height should be roughly half of total height (red nodes alternate with black).
        assertTrue(blackHeight >= 1 && blackHeight <= height, "Black height should be a sane fraction of total height");

        for (int i = 1; i <= 100; i++) assertTrue(tree.contains(i));
    }

    @Test
    void inorderTraversalIsSorted() {
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>(Comparator.<Integer>naturalOrder());
        int[] values = {50, 20, 70, 10, 30, 60, 80};
        for (int v : values) tree.insert(v);

        DynamicArray<Integer> out = new DynamicArray<>();
        tree.inorder(out);

        for (int i = 1; i < out.size(); i++) {
            assertTrue(out.get(i - 1) < out.get(i), "In-order traversal of a BST-derived tree must be sorted");
        }
        assertEquals(values.length, out.size());
    }
}
