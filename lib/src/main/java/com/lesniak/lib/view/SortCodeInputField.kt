package com.lesniak.lib.view

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.TextWatcher
import android.text.method.NumberKeyListener
import android.util.AttributeSet
import androidx.databinding.*
import androidx.databinding.adapters.ListenerUtil
import com.google.android.material.textfield.TextInputEditText
import com.lesniak.lib.R
import com.lesniak.lib.utils.SortCodeFormatter
import com.lesniak.lib.utils.TextWatcherAdapter

@BindingMethods(
    value = [BindingMethod(
        type = SortCodeInputField::class,
        attribute = "sortCode",
        method = "setSortCode"
    )]
)
@InverseBindingMethods(
    value = [InverseBindingMethod(
        type = SortCodeInputField::class,
        attribute = "sortCode",
        event = "sortCodeAttrChanged",
        method = "getSortCode"
    )]
)
class SortCodeInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    private var isInitialized: Boolean = false
    private var initialValue: String? = null

    var sortCode: String?
        get() {
            return SortCodeFormatter.stripFormatting(text?.toString(), DELIMITER)
        }
        set(newValue) {

            if (!isInitialized) {
                initialValue = newValue
                return
            }

            if (sortCode != newValue && isInitialized)
                text?.clear()
                    .also { append(SortCodeFormatter.stripFormatting(newValue, DELIMITER)) }
        }

    private val watcherFormatter: TextWatcher by lazy {
        object : TextWatcherAdapter() {

            override fun afterTextChanged(s: Editable?) {
                s ?: return

                val formattedSortCode =
                    SortCodeFormatter.getFormattedSortCode(s.toString(), DELIMITER)
                if (s.toString() == formattedSortCode) return
                s.replace(0, s.length, formattedSortCode)
            }
        }
    }

    private val keyListener: NumberKeyListener by lazy {
        object : NumberKeyListener() {
            override fun getInputType(): Int = TYPE_CLASS_NUMBER
            override fun getAcceptedChars() = "0123456789$DELIMITER".toCharArray()
        }
    }

    private val lengthFilter: InputFilter by lazy {
        InputFilter.LengthFilter(MAX_CHAR)
    }

    companion object {

        private const val DELIMITER = "-"
        private const val MAX_CHAR = 8 // Including delimiters

        @BindingAdapter("sortCodeAttrChanged")
        @JvmStatic
        fun setSortCodeChanged(
            field: SortCodeInputField,
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

        addTextChangedListener(watcherFormatter)
        setKeyListener(keyListener)
        filters = listOf(lengthFilter).toTypedArray()

        isInitialized = true

        if (initialValue != null) {
            sortCode = initialValue
            initialValue = null
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        sortCode = text?.toString()
    }

}