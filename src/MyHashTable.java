public class MyHashTable<K, V> {
    private static class HashNode<K, V> {
        private final K key;
        private V value;
        private HashNode<K, V> next;

        private HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;

    public MyHashTable() {
        chainArray = createChainArray(M);
    }

    public MyHashTable(int M) {
        if (M <= 0) {
            throw new IllegalArgumentException("Number of buckets must be positive");
        }
        this.M = M;
        chainArray = createChainArray(M);
    }

    @SuppressWarnings("unchecked")
    private HashNode<K, V>[] createChainArray(int capacity) {
        return (HashNode<K, V>[]) new HashNode[capacity];
    }

    private int hash(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];

        while (current != null) {
            if (same(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = chainArray[index];
        chainArray[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];

        while (current != null) {
            if (same(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        HashNode<K, V> previous = null;

        while (current != null) {
            if (same(current.key, key)) {
                if (previous == null) {
                    chainArray[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    public boolean contains(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                if (same(current.value, value)) {
                    return true;
                }
                current = current.next;
            }
        }

        return false;
    }

    public K getKey(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                if (same(current.value, value)) {
                    return current.key;
                }
                current = current.next;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public int[] bucketSizes() {
        int[] sizes = new int[M];

        for (int i = 0; i < M; i++) {
            int count = 0;
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                count++;
                current = current.next;
            }
            sizes[i] = count;
        }

        return sizes;
    }

    public void printBucketSizes() {
        int[] sizes = bucketSizes();

        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Bucket " + i + ": " + sizes[i]);
        }
    }

    private boolean same(Object first, Object second) {
        return first == second || (first != null && first.equals(second));
    }
}
