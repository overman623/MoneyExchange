package com.makestorming.moneyexchange

import android.content.Context

sealed class ResourceString {
    abstract fun format(context: Context): String
}
class IdResourceString(val id: Int): ResourceString() {
    override fun format(context: Context): String {
        return context.getString(id)
    }
}
class TextResourceString(val text: String): ResourceString() {
    override fun format(context: Context): String {
        return text
    }
}
class FormatResourceString(val id: Int, val values: Array<Any>): ResourceString() {
    override fun format(context: Context): String {
        return context.getString(id, *values)
    }
}
val list: Array<String> = arrayOf(
            "de",
            "es",
            "fr",
            "it",
            "nl",
            "pt",
            "ru",
            "cn",
            "ja",
            "tw",
            "id",
            "my",
            "cs",
            "no",
            "pl",
            "sv",
            "vn",
            "tr",
            "gr",
            "hi",
            "th",
            "ko"
)