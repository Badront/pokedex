package com.badront.pokedex.core.ext.androidx.palette.graphics

import android.graphics.Bitmap
import android.os.AsyncTask
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Returns [ColorPalette] whenever coil successfully loaded image
 */
inline fun ImageRequest.Builder.getPalette(crossinline block: (ColorPalette?) -> Unit) {
    allowHardware(false)
    listener { _, result ->
        result.getPalette(block)
    }
}

inline fun SuccessResult.getPalette(crossinline block: (ColorPalette?) -> Unit) {
    drawable.toBitmap().getPalette(block)
}

/**
 * Returns [ColorPalette] from bitmap
 */
inline fun Bitmap.getPalette(crossinline block: (ColorPalette?) -> Unit): AsyncTask<Bitmap, Void, Palette> {
    return Palette.from(this).generate { palette ->
        palette?.dominantSwatch?.let { swatch ->
            block(
                ColorPalette(
                    primaryColor = swatch.rgb,
                    onPrimaryColor = swatch.titleTextColor
                )
            )
        } ?: block(null)
    }
}

suspend inline fun Bitmap.getPalette(): ColorPalette? {
    return suspendCancellableCoroutine { continuation ->
        val task = getPalette { colorPalette ->
            if (continuation.isActive) {
                continuation.resume(colorPalette)
            }
        }
        continuation.invokeOnCancellation {
            task.cancel(false)
        }
    }
}