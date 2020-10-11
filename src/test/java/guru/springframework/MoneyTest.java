package guru.springframework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    void testMultiplication() {
        Money dollar = Money.getDollar(5);
        assertEquals(Money.getDollar(10), dollar.doTimes(2));
        assertEquals(Money.getDollar(15), dollar.doTimes(3));

        Money franc = Money.getFranc(5);
        assertEquals(Money.getFranc(10), franc.doTimes(2));
        assertEquals(Money.getFranc(15), franc.doTimes(3));
    }

    @Test
    void testEquality() {
        assertEquals(Money.getDollar(5), Money.getDollar(5));
        assertNotEquals(Money.getDollar(5), Money.getDollar(8));
        assertNotEquals(Money.getDollar(5), Money.getFranc(5));

        assertEquals(Money.getFranc(5), Money.getFranc(5));
        assertNotEquals(Money.getFranc(5), Money.getFranc(8));
    }

    @Test
    void testCurrency() {
        assertEquals("USD", Money.getDollar(1).getCurrency());
        assertEquals("CHF", Money.getFranc(1).getCurrency());
    }

    @Test
    void testSimpleAddition() {
        Money dollar = Money.getDollar(5);
        Expression sum = dollar.doPlus(dollar);
        Bank bank = new Bank();
        Money reduced = bank.doReduce(sum, "USD");
        assertEquals(Money.getDollar(10), reduced);
    }

    @Test
    void testMoneyPlusReturnsSum() {
        Money dollar = Money.getDollar(5);
        Expression result = dollar.doPlus(dollar);
        Sum sum = (Sum) result;
        assertEquals(dollar, sum.augmend);
        assertEquals(dollar, sum.addmend);
    }

    @Test
    void testReduceSum() {
        Expression sum = new Sum(Money.getDollar(3), Money.getDollar(4));
        Bank bank = new Bank();
        Money result = bank.doReduce(sum, "USD");
        assertEquals(Money.getDollar(7), result);
    }

    @Test
    void testMoneyReduce() {
        Bank bank = new Bank();
        Money result = bank.doReduce(Money.getDollar(1), "USD");
        assertEquals(Money.getDollar(1), result);
    }

    @Test
    void testMoneyReduceWithDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.doReduce(Money.getFranc(2), "USD");
        assertEquals(Money.getDollar(1), result);
    }

    @Test
    void testIdentityRate() {
        assertEquals(1, new Bank().getRate("USD", "USD"));
        assertEquals(1, new Bank().getRate("CHF", "CHF"));
    }

    @Test
    void testMixedAddition() {
        Expression fiveBucks = Money.getDollar(5);
        Expression tenFrancs = Money.getFranc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.doReduce(fiveBucks.doPlus(tenFrancs), "USD");
        assertEquals(Money.getDollar(10), result);
    }

    @Test
    void testSumPlusMoney() {
        Expression fiveBucks = Money.getDollar(5);
        Expression tenFrancs = Money.getFranc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).doPlus(fiveBucks);
        Money result = bank.doReduce(sum, "USD");
        assertEquals(Money.getDollar(15), result);
    }

    @Test
    void testSumTimes() {
        Expression fiveBucks = Money.getDollar(5);
        Expression tenFrancs = Money.getFranc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).doTimes(2);
        Money result = bank.doReduce(sum, "USD");
        assertEquals(Money.getDollar(20), result);
    }
}
