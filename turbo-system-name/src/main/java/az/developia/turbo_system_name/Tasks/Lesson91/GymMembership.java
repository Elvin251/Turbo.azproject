package az.developia.turbo_system_name.Tasks.Lesson91;

public class GymMembership {
    protected String name, type;
    protected int duration;

    public GymMembership(String n, String t, int d) {
        name = n;
        type = t;
        duration = d;
    }

    public int fee() {
        return duration * 50;
    }



    public class PremiumMembership extends GymMembership {
        private boolean trainer;
        private boolean spa;

        public PremiumMembership(String n, String t, int d, boolean tr, boolean s) {
            super(n, t, d);
            trainer = tr;
            spa = s;
        }

        public boolean hasOffer() {
            return duration > 6;
        }
    }
}