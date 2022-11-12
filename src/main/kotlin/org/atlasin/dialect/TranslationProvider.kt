package org.atlasin.dialect

fun interface TranslationProvider {
    fun provide(ctx : TranslationContext) : String
}