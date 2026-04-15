package az.developia.turbo_system_name.Tasks.Lesson91;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printInfo() {
        System.out.println(name + " - " + age);
    }

    public static void main(String[] args) {
        Person p1 = new Person("Ali", 20);
        Person p2 = new Person("Veli", 25);

        p1.printInfo();
        p2.printInfo();
    }
}