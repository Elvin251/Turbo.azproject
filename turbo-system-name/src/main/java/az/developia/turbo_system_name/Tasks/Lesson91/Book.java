package az.developia.turbo_system_name.Tasks.Lesson91;
import java.util.ArrayList;
public class Book {
    private String title;
    private String author;
    private String isbn;

    public Book(String t, String a, String i) {
        title = t;
        author = a;
        isbn = i;
    }

    public String getTitle() { return title; }
    public class BookManager {
        private ArrayList<Book> books = new ArrayList<>();

        public void addBook(Book b) {
            books.add(b);
        }

        public void removeBook(Book b) {
            books.remove(b);
        }
    }
}


