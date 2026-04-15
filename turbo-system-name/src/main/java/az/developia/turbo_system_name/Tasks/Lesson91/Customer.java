package az.developia.turbo_system_name.Tasks.Lesson91;

import java.util.ArrayList;

public class Customer {
    protected String name;
    protected String email;
    protected ArrayList<Double> purchases = new ArrayList<>();

    public Customer(String n, String e) {
        name = n;
        email = e;
    }

    public void addPurchase(double amount) {
        purchases.add(amount);
    }

    public double total() {
        double sum = 0;
        for (double p : purchases) sum += p;
        return sum;
    }


    public class LoyalCustomer extends Customer {
        private double discount;

        public LoyalCustomer(String n, String e, double d) {
            super(n, e);
            discount = d;
        }

        public double getDiscountedTotal() {
            return total() * (1 - discount);
        }
    }
}