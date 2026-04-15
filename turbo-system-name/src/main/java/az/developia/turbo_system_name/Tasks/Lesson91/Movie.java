package az.developia.turbo_system_name.Tasks.Lesson91;

import java.util.ArrayList;

public class Movie {
    private String title;
    private String director;
    private ArrayList<String> reviews = new ArrayList<>();

    public Movie(String t, String d) {
        title = t;
        director = d;
    }

    public void addReview(String r) {
        reviews.add(r);
    }

    public void showReviews() {
        for (String r : reviews) {
            System.out.println(r);
        }
    }
}