package az.developia.turbo_system_name.Tasks.Lesson92;

import java.util.*;

public class Task9 {
    public static void main(String[] args) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Red");

        ArrayList<String> list2 = new ArrayList<>(list1);

        System.out.println(list2);
    }
}