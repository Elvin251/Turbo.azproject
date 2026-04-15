package az.developia.turbo_system_name.Tasks.Lesson91;

import java.util.ArrayList;

public class Student {
    private String name;
    private ArrayList<String> courses = new ArrayList<>();

    public Student(String n) {
        name = n;
    }

    public void addCourse(String c) { courses.add(c); }
    public void removeCourse(String c) { courses.remove(c); }
}