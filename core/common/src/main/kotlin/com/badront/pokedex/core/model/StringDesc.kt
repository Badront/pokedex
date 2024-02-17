package com.badront.pokedex.core.model

import android.os.Parcelable
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

sealed class StringDesc : Parcelable {
    @Parcelize
    class StringR(
        @StringRes val resourceId: Int,
        val params: @RawValue Array<Any> = emptyArray()
    ) : StringDesc() {

        override fun equals(other: Any?): Boolean {
            if (javaClass != other?.javaClass) return false
            other as StringR
            if (resourceId != other.resourceId) return false
            return params.contentDeepEquals(other.params)
        }

        override fun hashCode(): Int {
            var result = resourceId.hashCode()
            result = 31 * result + params.contentDeepHashCode()
            return result
        }
    }

    @Parcelize
    class PluralR(
        @PluralsRes val resourceId: Int,
        val quantity: Int,
        val params: @RawValue Array<Any> = emptyArray()
    ) : StringDesc() {

        override fun equals(other: Any?): Boolean {
            if (javaClass != other?.javaClass) return false
            other as PluralR
            if (resourceId != other.resourceId) return false
            if (quantity != other.quantity) return false
            return params.contentDeepEquals(other.params)
        }

        override fun hashCode(): Int {
            var result = resourceId.hashCode()
            result = 31 * result + quantity.hashCode()
            result = 31 * result + params.contentDeepHashCode()
            return result
        }
    }

    @Parcelize
    data class StringStr(
        val str: String?
    ) : StringDesc() {

        fun getValue(): String? {
            return str
        }
    }

    @Parcelize
    data class CharSequenceStr(
        val str: CharSequence?
    ) : StringDesc() {

        fun getValue(): CharSequence? {
            return str
        }
    }
}

fun String.toDesc(): StringDesc = StringDesc.StringStr(this)
fun CharSequence.toDesc(): StringDesc = StringDesc.CharSequenceStr(this)