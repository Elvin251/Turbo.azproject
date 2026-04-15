package az.developia.turbo_system_name.Tasks.Lesson91;

public class Building {
    protected String address;
    protected int floors;
    protected double area;

    public Building(String a, int f, double ar) {
        address = a;
        floors = f;
        area = ar;
    }



    public class ResidentialBuilding extends Building {
        private int apartments;

        public ResidentialBuilding(String a, int f, double ar, int ap) {
            super(a, f, ar);
            apartments = ap;
        }

        public double rent() {
            return apartments * 500;
        }
    }



    public class CommercialBuilding extends Building {
        private int offices;

        public CommercialBuilding(String a, int f, double ar, int o) {
            super(a, f, ar);
            offices = o;
        }

        public double rent() {
            return offices * 1000;
        }
    }
}