package com.badront.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.badront.pokedex.core.presentation.BaseFragmentLifecycleCallbacks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        supportFragmentManager.registerFragmentLifecycleCallbacks(BaseFragmentLifecycleCallbacks(), true)
    }

    override fun onBackPressed() {
        if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            super.onBackPressed()
        } else {
            finishAfterTransition()
        }
    }
}