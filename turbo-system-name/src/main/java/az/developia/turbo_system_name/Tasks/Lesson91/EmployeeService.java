package az.developia.turbo_system_name.Tasks.Lesson91;

import java.time.LocalDate;

public class EmployeeService {
    private String name;
    private double salary;
    private LocalDate hireDate;

    public EmployeeService(String n, double s, LocalDate d) {
        name = n;
        salary = s;
        hireDate = d;
    }

    public int getYearsOfService() {
        return LocalDate.now().getYear() - hireDate.getYear();
    }
}