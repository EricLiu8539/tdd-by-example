package guru.springframework;

public class Money implements Expression {

    protected int amount;
    protected String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    // Factory Pattern
    public static Money getDollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money getFranc(int amount) {
        return new Money(amount, "CHF");
    }

    public String getCurrency() {
        return currency;
    }

    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount && currency.equals(money.currency);
    }

    @Override
    public Expression doPlus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Money doReduce(Bank bank, String toCurrency) {
        return new Money(amount / bank.getRate(this.currency, toCurrency), toCurrency);
    }

    @Override
    public Expression doTimes(int multiplier) {
        return new Money(amount * multiplier, this.currency);
    }
}
