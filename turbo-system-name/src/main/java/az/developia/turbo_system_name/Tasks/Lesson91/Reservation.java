package az.developia.turbo_system_name.Tasks.Lesson91;

public class Reservation {
    protected int id;
    protected String name;
    protected String date;

    public Reservation(int i, String n, String d) {
        id = i;
        name = n;
        date = d;
    }




    public class ResortReservation extends Reservation {
        private int room;

        public ResortReservation(int i, String n, String d, int r) {
            super(i, n, d);
            room = r;
        }
    }

    public class RailwayReservation extends Reservation {
        private int seat;

        public RailwayReservation(int i, String n, String d, int s) {
            super(i, n, d);
            seat = s;
        }
    }
}