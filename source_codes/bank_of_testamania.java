import java.util.*;

// Banking System Implementation

// Abstract BankAccount Class
abstract class BankAccount {
    protected String accountNumber;
    protected double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        if (initialBalance < 0) throw new IllegalArgumentException("Initial balance cannot be negative.");
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
    }

    public abstract void applyMonthlyFee();
}

// CheckingAccount Class
class CheckingAccount extends BankAccount {
    private static final double MONTHLY_FEE = 5.00;

    public CheckingAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public void applyMonthlyFee() {
        if (balance >= MONTHLY_FEE) {
            balance -= MONTHLY_FEE;
        }
    }
}

// SavingsAccount Class
class SavingsAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.02; // 2% monthly interest

    public SavingsAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public void applyMonthlyFee() {
        balance += balance * INTEREST_RATE;
    }
}

// Bank Class
class Bank {
    private final Map<String, BankAccount> accounts = new HashMap<>();

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean transfer(String fromAccount, String toAccount, double amount) {
        BankAccount sender = accounts.get(fromAccount);
        BankAccount receiver = accounts.get(toAccount);

        if (sender == null || receiver == null) return false;
        if (amount <= 0 || sender.getBalance() < amount) return false;

        sender.withdraw(amount);
        receiver.deposit(amount);
        return true;
    }
}
