package com.ladyboomerang.bill;

import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.*;

public class BillServiceUnitTest
{
    @Test
    public void testCalculateChange_IsCorrect() throws Exception
    {
        BillService service = BillService.getInstance();
        Change change = service.calculateChange(new BigDecimal("10"), new BigDecimal("1.94"));

        assertEquals(8.06, change.value.doubleValue(), 0);
        assertEquals(4, change.numBanknotes);
        assertEquals(2, change.numCoins);
   }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateChange_BillIsGreaterThanAmountPaid()
    {
       BillService.getInstance().calculateChange(new BigDecimal("10"), new BigDecimal("20"));
    }
}