package az.developia.turbo_system_name.Tasks.Lesson91;

public class CustomerOrder {
    protected int orderId;
    protected String customer;
    protected String date;

    public CustomerOrder(int id, String c, String d) {
        orderId = id;
        customer = c;
        date = d;
    }

    public class OnlineOrder extends CustomerOrder {
        private String address;
        private String tracking;

        public OnlineOrder(int id, String c, String d, String a, String t) {
            super(id, c, d);
            address = a;
            tracking = t;
        }

        public void updateTracking(String t) {
            tracking = t;
        }
    }
}