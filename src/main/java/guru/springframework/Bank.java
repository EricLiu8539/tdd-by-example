package guru.springframework;

import java.util.HashMap;

public class Bank {

    private HashMap<Pair, Integer> rateMap = new HashMap<>();

    public Money doReduce(Expression source, String toCurrency) {
        return source.doReduce(this, toCurrency);
    }

    public int getRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return  1;
        }

        return rateMap.get(new Pair(fromCurrency, toCurrency));
    }

    public void addRate(String fromCurrency, String toCurrency, int rate) {
        rateMap.put(new Pair(fromCurrency, toCurrency), rate);
    }
}
