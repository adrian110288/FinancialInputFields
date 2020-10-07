package com.lesniak.lib.utils

object SortCodeFormatter : InputFormatter {

    private val copy = StringBuilder()
    const val DELIMITER = "-"
    private const val SORT_CODE_MAX_CHAR = 6
    const val SORT_CODE_MAX_CHAR_INCLUDES_DELIMITER = 8 // Including delimiters

    override fun getFormattedInput(input: String?): String {

        input ?: return ""

        val formattedInput = stripFormatting(input).take(SORT_CODE_MAX_CHAR)

        copy.clear()
        formattedInput.forEachIndexed { index, char ->
            if (index == 2 || index == 4) copy.append(DELIMITER)
            copy.append(char)
        }

        return copy.toString()
    }

    override fun stripFormatting(input: String?): String {
        return input?.replace(DELIMITER, "") ?: ""
    }
}