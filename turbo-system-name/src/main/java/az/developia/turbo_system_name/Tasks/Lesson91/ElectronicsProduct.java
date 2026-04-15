package az.developia.turbo_system_name.Tasks.Lesson91;

public class ElectronicsProduct {
    protected String id, name;
    protected double price;

    public ElectronicsProduct(String i, String n, double p) {
        id = i;
        name = n;
        price = p;
    }

    public double applyDiscount(double d) {
        return price - d;
    }

    public class WashingMachine extends ElectronicsProduct {
        private int warranty;

        public WashingMachine(String i, String n, double p, int w) {
            super(i, n, p);
            warranty = w;
        }

        public void extendWarranty(int extra) {
            warranty += extra;
        }
    }
}