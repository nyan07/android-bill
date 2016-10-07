package com.ladyboomerang.bill;

import java.math.BigDecimal;

public class Change
{
    public BigDecimal value;
    public int numCoins;
    public int numBanknotes;

    public Change(BigDecimal value)
    {
        this.value = value;
    }
}
