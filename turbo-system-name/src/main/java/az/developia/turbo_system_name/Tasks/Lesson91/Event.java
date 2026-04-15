package az.developia.turbo_system_name.Tasks.Lesson91;

public class Event {
    protected String name, date, location;

    public Event(String n, String d, String l) {
        name = n;
        date = d;
        location = l;
    }

    public class Seminar extends Event {
        private int speakers;

        public Seminar(String n, String d, String l, int s) {
            super(n, d, l);
            speakers = s;
        }
    }

    public class MusicalPerformance extends Event {
        private int performers;

        public MusicalPerformance(String n, String d, String l, int p) {
            super(n, d, l);
            performers = p;
        }
    }
}