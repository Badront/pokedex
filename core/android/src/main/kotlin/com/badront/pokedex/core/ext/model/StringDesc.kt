package com.badront.pokedex.core.ext.model

import android.content.Context
import android.widget.TextView
import com.badront.pokedex.core.ext.android.content.getQuantityStringKtx
import com.badront.pokedex.core.model.StringDesc

fun StringDesc.toCharSequence(context: Context): CharSequence? {
    return when (this) {
        is StringDesc.CharSequenceStr -> getValue()
        is StringDesc.PluralR -> context.getQuantityStringKtx(
            resourceId,
            quantity,
            *params.map { it.getValue(context) }.toTypedArray()
        )
        is StringDesc.StringR -> context.getString(
            resourceId,
            *params.map { it.getValue(context) }.toTypedArray()
        )
        is StringDesc.StringStr -> getValue()
    }
}

fun StringDesc.toString(context: Context): String? = toCharSequence(context)?.toString()

private fun Any.getValue(context: Context): Any? {
    return when (this) {
        is StringDesc -> this.toString(context)
        else -> this
    }
}

fun Context.getCharSequence(resource: StringDesc?): CharSequence? {
    return resource?.toString(this)
}

fun Context.getString(resource: StringDesc?): String {
    return resource?.toString(this).toString()
}

fun TextView.setText(resource: StringDesc?) {
    text = context.getCharSequence(resource)
}