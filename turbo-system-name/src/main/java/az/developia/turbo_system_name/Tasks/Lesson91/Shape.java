package az.developia.turbo_system_name.Tasks.Lesson91;

public abstract class Shape {
    public abstract double area();
    public abstract double perimeter();
    public class RectangleShape extends Shape {
        private double width, height;

        public RectangleShape(double w, double h) {
            width = w;
            height = h;
        }

        public double area() { return width * height; }
        public double perimeter() { return 2 * (width + height); }
    }

    public class CircleShape extends Shape {
        private double radius;

        public CircleShape(double r) {
            radius = r;
        }

        public double area() { return Math.PI * radius * radius; }
        public double perimeter() { return 2 * Math.PI * radius; }
    }
}