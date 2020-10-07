package com.lesniak.lib

import com.lesniak.lib.utils.SortCodeFormatter.getFormattedInput
import org.junit.Assert.assertTrue
import org.junit.Test

class SortCodeFormatterTest {

    @Test
    fun formatting_isCorrect() {

        assertTrue("1" == getFormattedInput("1"))
        assertTrue("11" == getFormattedInput("11"))
        assertTrue("11-1" == getFormattedInput("111"))
        assertTrue("11-11" == getFormattedInput("1111"))
        assertTrue("11-11-1" == getFormattedInput("11111"))
        assertTrue("11-11-11" == getFormattedInput("111111"))
        assertTrue("11-11-11" == getFormattedInput("1111111"))
        assertTrue("11-11-11" == getFormattedInput("11111111"))
    }

}