package az.developia.turbo_system_name.Tasks.Lesson91;

public class Vehicle {
    protected String make, model;
    protected int year;

    public Vehicle(String m, String mo, int y) {
        make = m;
        model = mo;
        year = y;
    }

    public class Car extends Vehicle {
        private int trunkSize;

        public Car(String m, String mo, int y, int t) {
            super(m, mo, y);
            trunkSize = t;
        }

        public void show() {
            System.out.println(make + " " + model + " trunk:" + trunkSize);
        }
    }
    public class Truck extends Vehicle {
        private int payload;

        public Truck(String m, String mo, int y, int p) {
            super(m, mo, y);
            payload = p;
        }

        public void show() {
            System.out.println(make + " " + model + " payload:" + payload);
        }
    }
}