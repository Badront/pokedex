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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shouldAddSystemTopPadding) {
            view.doOnApplyWindowInsets { _, insets, _, _ ->
                view.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top)
            }
        }
    }

    @CallSuper
    open fun onObserveData() {
        Timber.d("${javaClass.name} start data observing")
    }
}