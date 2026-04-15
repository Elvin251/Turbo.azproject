package az.developia.turbo_system_name.Tasks.Lesson91;
import java.util.ArrayList;
public class Account {
    private String name;
    private double balance;

    public Account(String n, double b) {
        name = n;
        balance = b;
    }

    public void deposit(double a) { balance += a; }
    public void withdraw(double a) { balance -= a; }

    public double getBalance() { return balance; }



    public class Bank {
        private ArrayList<Account> accounts = new ArrayList<>();

        public void addAccount(Account a) { accounts.add(a); }
        public void removeAccount(Account a) { accounts.remove(a); }
    }
}