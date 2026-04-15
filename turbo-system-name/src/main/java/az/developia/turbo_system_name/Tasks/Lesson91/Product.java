package az.developia.turbo_system_name.Tasks.Lesson91;
import java.util.ArrayList;
public class Product {
    String name;
    int quantity;

    public Product(String n, int q) {
        name = n;
        quantity = q;
    }


    public class Inventory {
        private ArrayList<Product> products = new ArrayList<>();

        public void add(Product p) { products.add(p); }
        public void remove(Product p) { products.remove(p); }

        public void checkLowStock() {
            for (Product p : products) {
                if (p.quantity < 5) {
                    System.out.println(p.name + " LOW STOCK");
                }
            }
        }
    }
}