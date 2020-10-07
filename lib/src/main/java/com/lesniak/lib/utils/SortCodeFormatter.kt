package com.lesniak.lib.utils

object SortCodeFormatter {

    private val sb: StringBuilder by lazy { StringBuilder() }
    const val DELIMITER = "-"
    private const val SORT_CODE_MAX_CHAR = 6
    const val SORT_CODE_MAX_CHAR_INCLUDES_DELIMITER = 8 // Including delimiters

    @JvmStatic
    fun getFormattedSortCode(input: String?): String {

        input ?: return ""

        val copy = stripFormatting(input).take(SORT_CODE_MAX_CHAR)

        sb.clear()

        copy.forEachIndexed { index, char ->
            if (index == 2 || index == 4) sb.append(DELIMITER)
            sb.append(char)
        }

        return sb.toString()
    }

    @JvmStatic
    fun stripFormatting(sortCode: String?): String {
        return sortCode?.replace(DELIMITER, "") ?: ""
    }
}