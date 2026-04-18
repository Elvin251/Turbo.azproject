package az.developia.turbo_system_name.Tasks.Lesson92;

import java.util.*;

public class Task15 {
    public static void main(String[] args) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Red");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("Blue");

        list1.addAll(list2);

        System.out.println(list1);
    }
}
