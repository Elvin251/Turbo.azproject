package az.developia.turbo_system_name.Tasks.Lesson91;

public class Employee {
    private String name;
    private String jobTitle;
    private double salary;

    public Employee(String n, String j, double s) {
        name = n;
        jobTitle = j;
        salary = s;
    }

    public void increaseSalary(double amount) {
        salary += amount;
    }
}