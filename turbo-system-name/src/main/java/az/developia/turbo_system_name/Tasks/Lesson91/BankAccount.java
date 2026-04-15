package az.developia.turbo_system_name.Tasks.Lesson91;

public class BankAccount {
    private String accountNumber;
    private String holderName;
    protected double balance;

    public BankAccount(String acc, String name, double b) {
        accountNumber = acc;
        holderName = name;
        balance = b;
    }

    public void deposit(double a) { balance += a; }
    public void withdraw(double a) { balance -= a; }

    public double getBalance() { return balance; }

    public class SavingsAccount extends BankAccount {
        private double interestRate;

        public SavingsAccount(String acc, String name, double b, double rate) {
            super(acc, name, b);
            interestRate = rate;
        }

        public void applyInterest() {
            balance += balance * interestRate;
        }
    }
}