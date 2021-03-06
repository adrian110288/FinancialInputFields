package com.lesniak.lib.view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputFilter
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.NumberKeyListener
import android.util.AttributeSet
import androidx.core.view.updatePadding
import androidx.databinding.*
import androidx.databinding.adapters.ListenerUtil
import com.google.android.material.textfield.TextInputEditText
import com.lesniak.lib.R
import com.lesniak.lib.utils.AmountFormatter
import com.lesniak.lib.utils.AmountFormatter.DECIMAL_DELIMITER
import com.lesniak.lib.utils.AmountFormatter.FORMAT_DELIMITER
import com.lesniak.lib.utils.TextWatcherAdapter
import java.math.BigDecimal


@BindingMethods(
    value = [BindingMethod(
        type = AmountInputField::class,
        attribute = "amount",
        method = "setAmount"
    )]
)
@InverseBindingMethods(
    value = [InverseBindingMethod(
        type = AmountInputField::class,
        attribute = "amount",
        event = "amountAttrChanged",
        method = "getAmount"
    )]
)
class AmountInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var isInitialized: Boolean = false
    private var initialValue: BigDecimal? = null

    private var originalLeftPadding: Float = 0f

    var amount: BigDecimal?
        get() = AmountFormatter.inputToBigDecimal(text?.toString())
        set(newValue) {

            if (!isInitialized) {
                initialValue = newValue
                return
            }

            if (amount != newValue && isInitialized)
                text?.clear()
                    .also { append("") }
        }

    private val inputFilter: InputFilter by lazy {

        object : InputFilter {
            override fun filter(
                source: CharSequence,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence {

                val current = text?.toString() ?: ""

                if (source == FORMAT_DELIMITER.toString()) return ""
                if (source == DECIMAL_DELIMITER.toString() && current.contains(DECIMAL_DELIMITER)) return ""
                if (source == "-" && current.contains('-')) return ""
                if (source == "-" && dstart != 0) return ""

                return source
            }

        }
    }

    private val watcherFormatter: TextWatcher by lazy {
        object : TextWatcherAdapter() {

            override fun afterTextChanged(s: Editable?) {
                s ?: return

                val formattedAmount =
                    AmountFormatter.getFormattedInput(s.toString())

                if (s.toString() == formattedAmount) return
                s.replace(0, s.length, formattedAmount)
            }
        }
    }

    private val keyListener: NumberKeyListener by lazy {
        object : NumberKeyListener() {
            override fun getInputType(): Int = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL
            override fun getAcceptedChars() =
                "${CURRENCY_SYMBOL}0123456789-$FORMAT_DELIMITER$DECIMAL_DELIMITER".toCharArray()
        }
    }

    companion object {

        //TODO make it variable
        const val CURRENCY_SYMBOL = "£"
        const val CURRENCY_SYMBOL_SPACE_END = 8

        @BindingAdapter("amountAttrChanged")
        @JvmStatic
        fun setAttrChangedListener(
            field: AmountInputField,
            attrChange: InverseBindingListener?
        ) {

            val newValue = object : TextWatcherAdapter() {
                override fun afterTextChanged(s: Editable?) {
                    attrChange?.onChange()
                }
            }

            val oldValue = ListenerUtil.trackListener(field, newValue, R.id.textWatcher)
            oldValue?.let { field.removeTextChangedListener(it) }
            field.addTextChangedListener(newValue)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        setKeyListener(keyListener)
        filters = listOf(inputFilter).toTypedArray()
        addTextChangedListener(watcherFormatter)

        isInitialized = true

        if (initialValue != null) {
            amount = initialValue
            initialValue = null
        }

        val currencySymbolWidth = paint.measureText(CURRENCY_SYMBOL)

        originalLeftPadding = compoundPaddingStart.toFloat()
        updatePadding(left = (currencySymbolWidth + originalLeftPadding + CURRENCY_SYMBOL_SPACE_END).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(
            CURRENCY_SYMBOL,
            originalLeftPadding,
            getLineBounds(0, null).toFloat(),
            paint
        )
    }

}