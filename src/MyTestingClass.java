public class MyTestingClass {
    private final int id;
    private final String group;

    public MyTestingClass(int id, String group) {
        this.id = id;
        this.group = group;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + id;

        if (group != null) {
            for (int i = 0; i < group.length(); i++) {
                hash = 31 * hash + group.charAt(i);
            }
        }

        hash ^= hash >>> 16;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof MyTestingClass)) {
            return false;
        }

        MyTestingClass other = (MyTestingClass) obj;
        return id == other.id && same(group, other.group);
    }

    @Override
    public String toString() {
        return "MyTestingClass{id=" + id + ", group='" + group + "'}";
    }

    private boolean same(Object first, Object second) {
        return first == second || (first != null && first.equals(second));
    }
}
