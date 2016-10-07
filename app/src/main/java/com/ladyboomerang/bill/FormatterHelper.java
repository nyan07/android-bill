package com.ladyboomerang.bill;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

public class FormatterHelper
{
    private FormatterHelper(){};

    public static String toBRL(BigDecimal value)
    {
        return NumberFormat.getCurrencyInstance(MainActivity.DEFAULT_CURRENCY_LOCALE).format(value);
    }

    public static BigDecimal clearCurrency(String value)
    {
        String symbol = Currency.getInstance(MainActivity.DEFAULT_CURRENCY_LOCALE)
                .getSymbol(MainActivity.DEFAULT_CURRENCY_LOCALE);

        String cleanString = value.replaceAll("["+ symbol +",.]", "");

        return new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR)
                .divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
    }
}
