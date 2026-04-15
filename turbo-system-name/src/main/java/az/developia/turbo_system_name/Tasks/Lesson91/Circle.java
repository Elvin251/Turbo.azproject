package az.developia.turbo_system_name.Tasks.Lesson91;

public class Circle {
    private double radius;

    public Circle(double r) {
        radius = r;
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double circumference() {
        return 2 * Math.PI * radius;
    }
}