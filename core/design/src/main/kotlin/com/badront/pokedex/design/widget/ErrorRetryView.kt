package com.badront.pokedex.design.widget

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.setPadding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.badront.pokedex.design.R
import com.badront.pokedex.design.databinding.WErrorRetryViewBinding

class ErrorRetryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleId: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleId) {

    private val viewBinding: WErrorRetryViewBinding by viewBinding(attachToRoot = true)
    var onRetryClickListener: (() -> Unit)? = null
    var errorText: CharSequence
        get() = viewBinding.errorMessage.text
        set(value) {
            viewBinding.errorMessage.text = value
        }

    init {
        setBackgroundResource(R.drawable.ripple_rounded_16)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(context.resources.getDimensionPixelSize(R.dimen.offset_16))
        val array = context.obtainStyledAttributes(attrs, R.styleable.ErrorRetryView, defStyleId, 0)
        if (array.hasValue(R.styleable.ErrorRetryView_erv_textSize)) {
            val textSize = array.getDimension(
                R.styleable.ErrorRetryView_erv_textSize,
                context.resources.getDimension(R.dimen.text_16)
            )
            viewBinding.errorMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
        if (array.hasValue(R.styleable.ErrorRetryView_erv_textColor)) {
            val textColor = array.getColor(R.styleable.ErrorRetryView_erv_textColor, Color.WHITE)
            viewBinding.errorMessage.setTextColor(textColor)
        }
        if (array.hasValue(R.styleable.ErrorRetryView_erv_retryTint)) {
            viewBinding.loadingRetry.setColorFilter(
                array.getColor(R.styleable.ErrorRetryView_erv_retryTint, Color.BLACK),
                PorterDuff.Mode.MULTIPLY
            )
        }
        if (array.hasValue(R.styleable.ErrorRetryView_erv_text)) {
            viewBinding.errorMessage.text = array.getText(R.styleable.ErrorRetryView_erv_text)
        }
        array.recycle()
        viewBinding.root.setOnClickListener { onRetryClickListener?.invoke() }
    }

    fun setErrorTextColor(@ColorInt color: Int) {
        viewBinding.errorMessage.setTextColor(color)
    }

    fun setErrorTextSize(textSize: Float) {
        viewBinding.errorMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }

    fun setRetryTint(@ColorInt color: Int) {
        viewBinding.loadingRetry.setColorFilter(
            color,
            PorterDuff.Mode.MULTIPLY
        )
    }
}