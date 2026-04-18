package az.developia.turbo_system_name.Tasks.Lesson92;

import java.util.*;

public class Task14 {
    public static void main(String[] args) {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Blue");

        Collections.swap(colors, 0, 1);

        System.out.println(colors);
    }
}