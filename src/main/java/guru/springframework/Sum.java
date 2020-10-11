package guru.springframework;

public class Sum implements Expression {
    Expression augmend;
    Expression addmend;

    public Sum(Expression augmend, Expression addmend) {
        this.augmend = augmend;
        this.addmend = addmend;
    }

    @Override
    public Money doReduce(Bank bank, String toCurrency) {
        int amount = augmend.doReduce(bank, toCurrency).amount + addmend.doReduce(bank, toCurrency).amount;
        return new Money(amount, toCurrency);
    }

    @Override
    public Expression doPlus(Expression addend) {
        return new Sum(this, addmend);
    }

    @Override
    public Expression doTimes(int multiplier) {
        return new Sum(augmend.doTimes(multiplier), addmend.doTimes(multiplier));
    }
}
