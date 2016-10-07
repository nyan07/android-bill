package com.ladyboomerang.bill;

import java.math.BigDecimal;

public class BillService
{
    private static BigDecimal[] BANKNOTES = new BigDecimal[]
            {
                    BigDecimal.valueOf(100),
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(5),
                    BigDecimal.valueOf(1)
            };

    private static BigDecimal[] COINS = new BigDecimal[]
            {
                BigDecimal.valueOf(0.5),
                BigDecimal.valueOf(0.1),
                BigDecimal.valueOf(0.05),
                BigDecimal.valueOf(0.01)
            };

    private static BillService instance;

    private BillService()
    {
    }

    public static BillService getInstance()
    {
        if (instance == null)
        {
            instance = new BillService();
        }

        return instance;
    }

    public Change calculateChange(BigDecimal amountPaid, BigDecimal bill)
    {
        if (amountPaid.compareTo(bill) == -1)
        {
            throw  new IllegalArgumentException();
        }

        Change change = new Change(amountPaid.subtract(bill));
        BigDecimal value = change.value;


        for (BigDecimal banknote : BANKNOTES)
        {
            while (value.compareTo(banknote) >= 0)
            {
                change.numBanknotes++;
                value = value.subtract(banknote);
            }
        }

        for (BigDecimal coin : COINS)
        {
            while (value.compareTo(coin) >= 0)
            {
                change.numCoins++;
                value = value.subtract(coin);
            }
        }

        return change;
    }
}
