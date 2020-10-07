package com.lesniak.lib.utils

object AmountFormatter {

    private val copy = StringBuilder()
    private val split = mutableListOf<String>()

    @JvmStatic
    fun getFormattedAmount(input: String?): String {
        input ?: return ""

        copy.clear()
        copy.append(stripFormatting(input))

        /**
         * Split the input into 2 parts - whole part and fractional part that is used for inspection later on.
         * Example: input = "123.987" will result in a list containing:
         * split[0] = "123"
         * split[1] = "987"
         */
        split.clear()
        with(input.split(".")) {
            split.add(this.getOrElse(0) { "" })
            split.add(this.getOrElse(1) { "" })
        }

        /**
         * Routine that ensures 2 decimal points.
         * It trimmes the decimal part to contain max 2 characters.
         */
        val indexOfDecimalPoint = copy.indexOf('.')
        if (indexOfDecimalPoint != -1 && split[1].length > 2) {
            val trimmed = copy.substring(0, indexOfDecimalPoint + 3)
            copy.clear().append(trimmed)
        }

        /**
         * Firs part of the routine ensures that the whole part of the input does not allow more that
         * one 0 if the whole part only consists of 0s
         * E.g. Input "0000.97" will become "0".97
         */
        if (split[0].isNotEmpty() && split[0].all { it == '0' }) {
            val noLeadingZeroes = copy.replaceFirst("^0*".toRegex(), "0")
            copy.clear().append(noLeadingZeroes)
        }

        /**
         * The second part of the routine will replace all remove all leading 0s
         * E.g "000230.23" will become "230.23.
         * The first part of this if-else statement needs to run first.
         */
        else {
            val noLeadingZeroes = copy.replaceFirst("^0*".toRegex(), "")
            if (noLeadingZeroes != copy.toString()) {
                copy.clear().append(noLeadingZeroes)
            }
        }

        /**
         * Routine that ensures that decimal point is always led by 0.
         * When a decimal point is present at the first position in the input,
         * the leading 0 is added before it
         */
        if (copy.getOrNull(0) == '.')
            copy.replace(0, 1, "0.")

//        return copy.toString()

        return if (copy.isEmpty()) ""
        else {

            val split = copy.split(".")

//            copy.clear()
//            copy.append(DecimalFormat("#,###").format(split[0].toDouble()))
//            copy.append(".")
//            copy.append(split.getOrElse(1){""}.toDouble())

            return copy.toString()

        }
    }

    private fun stripFormatting(input: String?): String {
        return input?.replace(",", "") ?: ""
    }
}