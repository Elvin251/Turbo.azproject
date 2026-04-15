package az.developia.turbo_system_name.Tasks.Lesson91;

public class Airplane {
    private String flightNumber;
    private String destination;
    private boolean delayed;

    public Airplane(String f, String d) {
        flightNumber = f;
        destination = d;
    }

    public void delay() { delayed = true; }

    public void checkStatus() {
        System.out.println(delayed ? "Delayed" : "On Time");
    }
}