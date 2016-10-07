package com.ladyboomerang.bill;

import org.junit.Test;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

public class FormatterHelperUnitTest
{
    @Test
    public void testToBLR() throws Exception
    {
        assertEquals("R$ 1,20", FormatterHelper.toBRL(BigDecimal.valueOf(1.2)));
    }

    @Test
    public void testClearCurrency() throws Exception
    {
        assertEquals(1.20, FormatterHelper.clearCurrency("R$1,20").doubleValue(), 0);
    }
}
