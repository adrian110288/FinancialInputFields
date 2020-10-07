package com.lesniak.lib.utils

import com.lesniak.lib.utils.SortCodeFormatter.getFormattedSortCode
import org.junit.Assert.assertTrue
import org.junit.Test

class SortCodeFormatterTest {

    @Test
    fun formatting_isCorrect() {

        assertTrue("1" == getFormattedSortCode("1"))
        assertTrue("11" == getFormattedSortCode("11"))
        assertTrue("11-1" == getFormattedSortCode("111"))
        assertTrue("11-11" == getFormattedSortCode("1111"))
        assertTrue("11-11-1" == getFormattedSortCode("11111"))
        assertTrue("11-11-11" == getFormattedSortCode("111111"))
        assertTrue("11-11-11" == getFormattedSortCode("1111111"))
        assertTrue("11-11-11" == getFormattedSortCode("11111111"))
    }

}