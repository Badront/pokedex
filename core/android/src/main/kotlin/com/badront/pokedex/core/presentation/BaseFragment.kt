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
    open val shouldAddSystemPadding: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shouldAddSystemPadding) {
            view.doOnApplyWindowInsets { _, insets, _, _ ->
                val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(
                    left = systemBarInsets.left,
                    top = systemBarInsets.top,
                    right = systemBarInsets.right,
                    bottom = systemBarInsets.bottom
                )
            }
        }
    }

    @CallSuper
    open fun onObserveData() {
        Timber.d("${javaClass.name} start data observing")
    }
}