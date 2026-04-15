package az.developia.turbo_system_name.Tasks.Lesson91;

import java.util.ArrayList;

public class Library {
    private ArrayList<String> books = new ArrayList<>();

    public void addBook(String b) { books.add(b); }
    public void removeBook(String b) { books.remove(b); }
}