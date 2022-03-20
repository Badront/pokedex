package com.badront.pokedex.core.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.badront.pokedex.core.ext.android.view.doOnApplyWindowInsets
import timber.log.Timber

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    open val shouldAddSystemTopPadding: Boolean = true
    open val shouldAddSystemBottomPadding: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shouldAddSystemTopPadding || shouldAddSystemBottomPadding) {
            view.doOnApplyWindowInsets { _, insets, _, _ ->
                if (shouldAddSystemTopPadding) {
                    view.updatePadding(
                        top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
                    )
                }
                if (shouldAddSystemBottomPadding) {
                    view.updatePadding(
                        bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                    )
                }
            }
        }
    }

    @CallSuper
    open fun onObserveData() {
        Timber.d("${javaClass.name} start data observing")
    }
}