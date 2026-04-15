package az.developia.turbo_system_name.Tasks.Lesson91;

public class TrafficLight {
    private String color;
    private int duration;

    public TrafficLight(String c, int d) {
        color = c;
        duration = d;
    }

    public void changeColor(String c) { color = c; }

    public boolean isRed() { return color.equals("Red"); }
    public boolean isGreen() { return color.equals("Green"); }
}