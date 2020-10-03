package com.lesniak.lib.utils

object SortCodeFormatter {

    private val sb: StringBuilder by lazy { StringBuilder() }

    @JvmStatic
    fun getFormattedSortCode(input: String?, delimiter: String): String {

        input ?: return ""

        val copy = stripFormatting(input, delimiter)

        sb.clear()

        copy.forEachIndexed { index, char ->
            if (index == 2 || index == 4) sb.append(delimiter)
            sb.append(char)
        }

        return sb.toString()
    }

    @JvmStatic
    fun stripFormatting(sortCode: String?, delimiter: String): String {
        return sortCode?.replace(delimiter, "") ?: ""
    }
}