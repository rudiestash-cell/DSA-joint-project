public class BSTIndex<T> {

    private class Node {
        int key;
        T value;
        Node left, right;

        Node(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(int key, T value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, int key, T value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key < node.key) {
            node.left = insert(node.left, key, value);
        } else if (key > node.key) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public T search(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.key) return current.value;
            current = (key < current.key) ? current.left : current.right;
        }
        return null;
    }

    public boolean contains(int key) {
        return search(key) != null;
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node node, int key) {
        if (node == null) return null;

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node successor = findMin(node.right);
            node.key = successor.key;
            node.value = successor.value;
            size++;
            node.right = delete(node.right, successor.key);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public int[] inOrderKeys() {
        int[] result = new int[size];
        inOrder(root, result, new int[]{0});
        return result;
    }

    private void inOrder(Node node, int[] result, int[] index) {
        if (node == null) return;
        inOrder(node.left, result, index);
        result[index[0]++] = node.key;
        inOrder(node.right, result, index);
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}