package az.developia.turbo_system_name.Tasks.Lesson91;

import java.util.ArrayList;

public class School {
    private ArrayList<String> students = new ArrayList<>();
    private ArrayList<String> teachers = new ArrayList<>();

    public void addStudent(String s) { students.add(s); }
    public void removeStudent(String s) { students.remove(s); }

    public void addTeacher(String t) { teachers.add(t); }
    public void removeTeacher(String t) { teachers.remove(t); }
}