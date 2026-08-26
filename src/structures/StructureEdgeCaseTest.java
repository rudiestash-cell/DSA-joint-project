package structures;

/**
 * Additional boundary and invalid-operation tests for custom data structures.
 */
public class StructureEdgeCaseTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        testEmptyDynamicArray();
        testInvalidDynamicArrayAccess();

        testEmptyStack();
        testEmptyQueue();
        testEmptyCircularQueue();
        testEmptyDeque();
        testEmptyDequeRemoveRear();

        testEmptyLinkedList();

        testDuplicateBST();

        testMissingHashKey();

        testEmptySet();
        testEmptyMap();

        testAlreadyConnectedDisjointSet();

        System.out.println();
        System.out.println("===== STRUCTURE EDGE-CASE TEST SUMMARY =====");
        System.out.println("PASSED: " + passed);
        System.out.println("FAILED: " + failed);

        if (failed > 0) {
            throw new AssertionError(
                    "Some structure edge-case tests failed."
            );
        }
    }

    private static void check(
            boolean condition,
            String testName) {

        if (condition) {
            passed++;
            System.out.println("PASS: " + testName);
        } else {
            failed++;
            System.out.println("FAIL: " + testName);
        }
    }

    private static void testEmptyDynamicArray() {

        DynamicArray<Integer> array =
                new DynamicArray<>();

        check(
                array.size() == 0,
                "Empty DynamicArray has size 0"
        );
    }

    private static void testInvalidDynamicArrayAccess() {

        DynamicArray<Integer> array =
                new DynamicArray<>();

        boolean threwException = false;

        try {
            array.get(0);
        } catch (Exception e) {
            threwException = true;
        }

        check(
                threwException,
                "DynamicArray rejects invalid access"
        );
    }

    private static void testEmptyStack() {

        Stack<Integer> stack =
                new Stack<>();

        boolean threwException = false;

        try {
            stack.pop();
        } catch (Exception e) {
            threwException = true;
        }

        check(
                threwException,
                "Empty Stack throws on pop (does not silently return garbage)"
        );
    }

    private static void testEmptyQueue() {

        Queue<Integer> queue =
                new Queue<>();

        boolean threwException = false;

        try {
            queue.dequeue();
        } catch (Exception e) {
            threwException = true;
        }

        check(
                threwException,
                "Empty Queue throws on dequeue (does not silently return garbage)"
        );
    }

    private static void testEmptyCircularQueue() {

        CircularQueue<Integer> queue =
                new CircularQueue<>(2);

        boolean threwException = false;

        try {
            queue.dequeue();
        } catch (Exception e) {
            threwException = true;
        }

        check(
                threwException,
                "Empty CircularQueue throws on dequeue (does not silently return garbage)"
        );
    }

    private static void testEmptyDeque() {

        Deque<Integer> deque =
                new Deque<>();

        boolean threwException = false;

        try {
            deque.removeFront();
        } catch (Exception e) {
            threwException = true;
        }

        check(
                threwException,
                "Empty Deque throws on removeFront (does not silently return garbage)"
        );
    }

    private static void testEmptyDequeRemoveRear() {

        Deque<Integer> deque =
                new Deque<>();

        boolean threwException = false;

        try {
            deque.removeRear();
        } catch (Exception e) {
            threwException = true;
        }

        check(
                threwException,
                "Empty Deque throws on removeRear (does not silently return garbage)"
        );
    }

    private static void testEmptyLinkedList() {

        DoublyLinkedList<Integer> list =
                new DoublyLinkedList<>();

        check(
                list.size() == 0,
                "Empty linked list has size 0"
        );
    }

    private static void testDuplicateBST() {

        BinarySearchTree<Integer> tree =
                new BinarySearchTree<>(
                        (a, b) -> a - b
                );

        tree.insert(10);
        tree.insert(10);

        check(
                tree.contains(10),
                "BST contains duplicate key"
        );
    }

    private static void testMissingHashKey() {

        HashTable<String, Integer> table =
                new HashTable<>(4);

        check(
                table.get("missing") == null,
                "HashTable returns null for missing key"
        );
    }

    private static void testEmptySet() {

        CustomSet<String> set =
                new CustomSet<>(4);

        check(
                set.size() == 0 &&
                !set.contains("missing"),
                "Empty CustomSet behaves correctly"
        );
    }

    private static void testEmptyMap() {

        CustomMap<String, Integer> map =
                new CustomMap<>(4);

        check(
                map.get("missing") == null,
                "Empty CustomMap returns null for missing key"
        );
    }

    private static void testAlreadyConnectedDisjointSet() {

        DisjointSet set =
                new DisjointSet(3);

        boolean firstUnion =
                set.union(0, 1);

        boolean secondUnion =
                set.union(0, 1);

        check(
                firstUnion && !secondUnion,
                "DisjointSet rejects duplicate union"
        );
    }
}