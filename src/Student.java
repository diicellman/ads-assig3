public class Student {
    private final String name;
    private final int age;
    private final double gpa;

    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Student)) {
            return false;
        }

        Student other = (Student) obj;
        return age == other.age
                && Double.compare(gpa, other.gpa) == 0
                && same(name, other.name);
    }

    @Override
    public int hashCode() {
        int hash = 17;

        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                hash = 31 * hash + name.charAt(i);
            }
        }

        long gpaBits = Double.doubleToLongBits(gpa);
        hash = 31 * hash + age;
        hash = 31 * hash + (int) (gpaBits ^ (gpaBits >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + "}";
    }

    private boolean same(Object first, Object second) {
        return first == second || (first != null && first.equals(second));
    }
}
