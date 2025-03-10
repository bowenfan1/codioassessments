import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// JUnit 5 Test Suite for Bank System
public class BankTestSuite {
    private Bank bank;
    private CheckingAccount checking;
    private SavingsAccount savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new CheckingAccount("CHK123", 500);
        savings = new SavingsAccount("SAV456", 300);
        bank.addAccount(checking);
        bank.addAccount(savings);
    }

    // BankAccount Tests
    @Test
    void depositShouldIncreaseBalance() {
        checking.deposit(100);
        assertEquals(600, checking.getBalance());
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        checking.withdraw(200);
        assertEquals(300, checking.getBalance());
    }

    @Test
    void withdrawMoreThanBalanceShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> checking.withdraw(600));
    }

    @Test
    void depositNegativeAmountShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> checking.deposit(-50));
    }

    // CheckingAccount Tests
    @Test
    void monthlyFeeShouldBeDeducted() {
        checking.applyMonthlyFee();
        assertEquals(495, checking.getBalance());
    }

    @Test
    void feeNotAppliedIfInsufficientBalance() {
        CheckingAccount smallBalance = new CheckingAccount("CHK999", 4);
        smallBalance.applyMonthlyFee();
        assertEquals(4, smallBalance.getBalance());
    }

    // SavingsAccount Tests
    @Test
    void interestShouldBeAppliedCorrectly() {
        savings.applyMonthlyFee();
        assertEquals(306, savings.getBalance()); // 2% interest applied
    }

    // Bank Tests
    @Test
    void transferBetweenAccountsShouldWork() {
        assertTrue(bank.transfer("CHK123", "SAV456", 100));
        assertEquals(400, checking.getBalance());
        assertEquals(400, savings.getBalance());
    }

    @Test
    void transferFailsIfInsufficientFunds() {
        assertFalse(bank.transfer("CHK123", "SAV456", 1000));
        assertEquals(500, checking.getBalance());
        assertEquals(300, savings.getBalance());
    }

    @Test
    void transferFailsIfInvalidAccount() {
        assertFalse(bank.transfer("CHK123", "FAKE123", 50));
    }
}
