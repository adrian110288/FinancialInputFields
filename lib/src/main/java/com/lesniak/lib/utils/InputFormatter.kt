package com.lesniak.lib.utils

interface InputFormatter {

    fun getFormattedInput(input: String?): String

    fun stripFormatting(input: String?): String

}