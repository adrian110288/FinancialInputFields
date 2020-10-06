package com.lesniak.lib

import com.lesniak.lib.utils.AmountFormatter.getFormattedAmount
import org.junit.Assert.assertTrue
import org.junit.Test

class AmountFormatterUnitTest {

    @Test
    fun formatting_isCorrect() {

        assertTrue("0." == getFormattedAmount("."))
        assertTrue("0.9" == getFormattedAmount(".9"))
        assertTrue("0.91" == getFormattedAmount(".912312"))
        assertTrue("112.2" == getFormattedAmount("112.2"))
        assertTrue("30.23" == getFormattedAmount("030.23"))
        assertTrue("30.23" == getFormattedAmount("0030.23"))
        assertTrue("0" == getFormattedAmount("0"))
        assertTrue("0" == getFormattedAmount("000"))
        assertTrue("0." == getFormattedAmount("0."))
        assertTrue("0.97" == getFormattedAmount("0000.97"))


//        assertTrue("-0.9" == getFormattedAmount("-.9"))

    }
}