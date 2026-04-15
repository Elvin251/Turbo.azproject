package az.developia.turbo_system_name.Tasks.Lesson91;

import java.util.*;

public class Restaurant {
    private HashMap<String, Double> menu = new HashMap<>();
    private ArrayList<Integer> ratings = new ArrayList<>();

    public void addItem(String name, double price) {
        menu.put(name, price);
    }

    public void removeItem(String name) {
        menu.remove(name);
    }

    public void addRating(int r) {
        ratings.add(r);
    }

    public double getAverageRating() {
        int sum = 0;
        for (int r : ratings) sum += r;
        return ratings.size() == 0 ? 0 : sum / (double) ratings.size();
    }
}