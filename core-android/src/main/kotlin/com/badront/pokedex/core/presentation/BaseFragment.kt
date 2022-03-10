package com.badront.pokedex.core.presentation

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    @CallSuper
    open fun onObserveData() {
        Timber.d("${javaClass.name} start data observing")
    }
}