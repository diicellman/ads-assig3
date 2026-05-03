import java.util.Random;

public class Main {
    public static void main(String[] args) {
        testMyHashTableOperations();
        System.out.println();
        testMyHashTableDistribution();
        System.out.println();
        testBST();
    }

    private static void testMyHashTableOperations() {
        System.out.println("MyHashTable basic operations");

        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>();
        MyTestingClass firstKey = new MyTestingClass(1, "alpha");
        MyTestingClass secondKey = new MyTestingClass(2, "beta");
        Student firstStudent = new Student("Aruzhan", 19, 3.7);
        Student secondStudent = new Student("Miras", 20, 3.4);
        Student updatedStudent = new Student("Aruzhan", 19, 3.9);

        table.put(firstKey, firstStudent);
        table.put(secondKey, secondStudent);
        table.put(firstKey, updatedStudent);

        System.out.println("Size after insert/update: " + table.size());
        System.out.println("Get first key: " + table.get(firstKey));
        System.out.println("Contains second student: " + table.contains(secondStudent));
        System.out.println("Key for second student: " + table.getKey(secondStudent));
        System.out.println("Removed second student: " + table.remove(secondKey));
        System.out.println("Get removed key: " + table.get(secondKey));
        System.out.println("Size after remove: " + table.size());
    }

    private static void testMyHashTableDistribution() {
        System.out.println("MyHashTable 10,000 random elements");

        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>();
        Random random = new Random(42);

        for (int i = 0; i < 10_000; i++) {
            MyTestingClass key = new MyTestingClass(i, "group-" + random.nextInt(1_000));
            Student value = new Student("Student-" + i, 18 + random.nextInt(8), 2.0 + random.nextDouble() * 2.0);
            table.put(key, value);
        }

        System.out.println("Size: " + table.size());
        table.printBucketSizes();
    }

    private static void testBST() {
        System.out.println("BST operations");

        BST<Integer, String> tree = new BST<>();
        int[] keys = {50, 30, 70, 20, 40, 60, 80, 35, 45, 65};

        for (int i = 0; i < keys.length; i++) {
            tree.put(keys[i], "value-" + keys[i]);
        }

        tree.put(40, "updated-40");

        System.out.println("Get 40: " + tree.get(40));
        System.out.println("Get missing 99: " + tree.get(99));
        System.out.println("BST size before deletions: " + tree.size());

        tree.delete(20);
        tree.delete(30);
        tree.delete(50);
        tree.delete(999);

        System.out.println("BST size after deletions: " + tree.size());

        for (BST.Entry<Integer, String> elem : tree) {
            System.out.println("key is " + elem.getKey() + " and value is " + elem.getValue());
        }
    }
}
