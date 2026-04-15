package az.developia.turbo_system_name.Tasks.Lesson91;

public class Rectangle {
    private double width;
    private double height;

    public Rectangle(double w, double h) {
        width = w;
        height = h;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }
}