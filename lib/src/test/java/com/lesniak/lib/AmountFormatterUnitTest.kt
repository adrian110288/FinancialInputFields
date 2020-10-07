package com.lesniak.lib

import com.lesniak.lib.utils.AmountFormatter.getFormattedInput
import com.lesniak.lib.utils.AmountFormatter.inputToBigDecimal
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_HALF_EVEN

class AmountFormatterUnitTest {

    @Test
    fun formatting_isCorrect() {

        assertTrue("0." == getFormattedInput("."))
        assertTrue("0.9" == getFormattedInput(".9"))
        assertTrue("0.91" == getFormattedInput(".912312"))
        assertTrue("112.2" == getFormattedInput("112.2"))
        assertTrue("30.23" == getFormattedInput("030.23"))
        assertTrue("30.23" == getFormattedInput("0030.23"))
        assertTrue("0" == getFormattedInput("0"))
        assertTrue("0" == getFormattedInput("00"))
        assertTrue("0" == getFormattedInput("000"))
        assertTrue("0." == getFormattedInput("0."))
        assertTrue("0.97" == getFormattedInput("0000.97"))
        assertTrue("1,000.97" == getFormattedInput("1000.97"))
        assertTrue("10,000.97" == getFormattedInput("10000.97"))
        assertTrue("100,000.97" == getFormattedInput("100000.97"))
        assertTrue("1,100,000.97" == getFormattedInput("1100000.97"))
        assertTrue("11,100,000.97" == getFormattedInput("11100000.97"))
        assertTrue("1,112,200,000.97" == getFormattedInput("1112200000.97"))
        assertTrue("112,200,000.97" == getFormattedInput("112200000.97"))
        assertTrue("11,111." == getFormattedInput("11111."))
        assertTrue("11,111" == getFormattedInput("11111"))


//        assertTrue("-0.9" == getFormattedInput("-.9"))

    }

    @Test
    fun convertToBigDecimal_isCorrect() {

        assertTrue(
            BigDecimal(0).setScale(2, ROUND_HALF_EVEN).compareTo(inputToBigDecimal(".0")) == 0
        )
        assertTrue(
            BigDecimal(10.55).setScale(2, ROUND_HALF_EVEN)
                .compareTo(inputToBigDecimal("10.55")) == 0
        )
        assertTrue(
            BigDecimal(1000.10).setScale(2, ROUND_HALF_EVEN)
                .compareTo(inputToBigDecimal("1,000.10")) == 0
        )
        assertTrue(
            BigDecimal(1000.00).setScale(2, ROUND_HALF_EVEN)
                .compareTo(inputToBigDecimal("1,000.")) == 0
        )
        assertTrue(
            BigDecimal(1000.10).setScale(2, ROUND_HALF_EVEN)
                .compareTo(inputToBigDecimal("1,000.1")) == 0
        )
        assertTrue(
            BigDecimal(1000.00).setScale(2, ROUND_HALF_EVEN)
                .compareTo(inputToBigDecimal("1,000")) == 0
        )
        assertTrue(
            BigDecimal(0.01).setScale(2, ROUND_HALF_EVEN).compareTo(inputToBigDecimal("0.01")) == 0
        )
        assertTrue(
            BigDecimal(0).setScale(2, ROUND_HALF_EVEN).compareTo(inputToBigDecimal("aw")) == 0
        )
        assertTrue(
            BigDecimal(0.00).setScale(2, ROUND_HALF_EVEN).compareTo(inputToBigDecimal("0.")) == 0
        )
        assertTrue(
            BigDecimal(0.00).setScale(2, ROUND_HALF_EVEN).compareTo(inputToBigDecimal(".")) == 0
        )
        assertTrue(
            BigDecimal(30.12).setScale(2, ROUND_HALF_EVEN)
                .compareTo(inputToBigDecimal("0030.1230")) == 0
        )
        assertTrue(
            BigDecimal(30.12).setScale(2, ROUND_HALF_EVEN)
                .compareTo(inputToBigDecimal("0030.1270")) == 0
        )

    }
}