import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left;
        private Node right;

        private Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public static class Entry<K, V> {
        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    public void put(K key, V val) {
        requireKey(key);

        if (root == null) {
            root = new Node(key, val);
            size++;
            return;
        }

        Node current = root;
        Node parent = null;
        int comparison = 0;

        while (current != null) {
            parent = current;
            comparison = key.compareTo(current.key);

            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                current.val = val;
                return;
            }
        }

        if (comparison < 0) {
            parent.left = new Node(key, val);
        } else {
            parent.right = new Node(key, val);
        }
        size++;
    }

    public V get(K key) {
        requireKey(key);

        Node current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                return current.val;
            }
        }

        return null;
    }

    public void delete(K key) {
        requireKey(key);

        Node parent = null;
        Node current = root;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison < 0) {
                parent = current;
                current = current.left;
            } else if (comparison > 0) {
                parent = current;
                current = current.right;
            } else {
                break;
            }
        }

        if (current == null) {
            return;
        }

        if (current.left != null && current.right != null) {
            Node successorParent = current;
            Node successor = current.right;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            current.key = successor.key;
            current.val = successor.val;
            parent = successorParent;
            current = successor;
        }

        Node child = current.left != null ? current.left : current.right;

        if (parent == null) {
            root = child;
        } else if (parent.left == current) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        size--;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new BSTIterator();
    }

    private void requireKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }
    }

    private class BSTIterator implements Iterator<Entry<K, V>> {
        private final ArrayDeque<Node> stack = new ArrayDeque<>();

        private BSTIterator() {
            pushLeft(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = stack.pop();
            pushLeft(node.right);
            return new Entry<>(node.key, node.val);
        }

        private void pushLeft(Node node) {
            Node current = node;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }
    }
}
