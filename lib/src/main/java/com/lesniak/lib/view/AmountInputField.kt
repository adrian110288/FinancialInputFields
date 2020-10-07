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
import androidx.databinding.*
import androidx.databinding.adapters.ListenerUtil
import com.google.android.material.textfield.TextInputEditText
import com.lesniak.lib.R
import com.lesniak.lib.utils.AmountFormatter
import com.lesniak.lib.utils.TextWatcherAdapter
import java.math.BigDecimal
import java.math.BigDecimal.ZERO


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

    var amount: BigDecimal?
        get() {
            return ZERO
        }
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

                if (source == ",") return ""
                if (source == "." && current.contains('.')) return ""
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
                    AmountFormatter.getFormattedAmount(s.toString())

                if (s.toString() == formattedAmount) return
                s.replace(0, s.length, formattedAmount)
            }
        }
    }

    private val keyListener: NumberKeyListener by lazy {
        object : NumberKeyListener() {
            override fun getInputType(): Int = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL
            override fun getAcceptedChars() = "£0123456789-,.".toCharArray()
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
    }

    private var originalLeftPadding: Float = 0f
    private var currencySymbolWidth: Float = 0f

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (originalLeftPadding == 0f) {

            if (currencySymbolWidth == 0f) {
                currencySymbolWidth = paint.measureText(CURRENCY_SYMBOL)
            }

            originalLeftPadding = compoundPaddingStart.toFloat()
            setPadding(
                (currencySymbolWidth + originalLeftPadding + CURRENCY_SYMBOL_SPACE_END).toInt(),
                paddingRight,
                paddingTop,
                paddingBottom
            )
        }
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