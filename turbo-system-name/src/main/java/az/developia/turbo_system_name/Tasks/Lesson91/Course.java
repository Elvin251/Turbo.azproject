package az.developia.turbo_system_name.Tasks.Lesson91;

public class Course {
    protected String name, instructor;
    protected int credits;

    public Course(String n, String i, int c) {
        name = n;
        instructor = i;
        credits = c;
    }

    public class OnlineCourse extends Course {
        private String platform;
        private int duration;

        public OnlineCourse(String n, String i, int c, String p, int d) {
            super(n, i, c);
            platform = p;
            duration = d;
        }

        public boolean hasCertificate() {
            return duration > 10;
        }
    }
}
