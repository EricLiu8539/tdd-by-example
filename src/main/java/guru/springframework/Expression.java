package guru.springframework;

public interface Expression {
    Money doReduce(Bank bank, String toCurrency);

    Expression doPlus(Expression addend);

    Expression doTimes(int multiplier);
}
