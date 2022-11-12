package org.atlasin.dialect

import fluent.bundle.FluentBundle
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

data class Translation(
    private val resourceBundle : FluentBundle,
    val translationKey : String
) {

    private val providers = mutableMapOf<String, TranslationProvider>()

    fun translate(ctx : TranslationContext) : String {
        return resourceBundle.format(translationKey, buildArguments(ctx))
    }

    fun provide(key : String, provider: TranslationProvider) {
        providers[key] = provider
    }

    private fun buildArguments(ctx : TranslationContext) : Map<String, String> {
        val args = mutableMapOf<String, String>()
        providers.map { it.key to it.value.provide(ctx)  }.also { args.putAll(it.toMap()) }
        DEFAULT_PROVIDERS.map { it.key to it.value.provide(ctx) }.also { args.putAll(it.toMap()) }
        STANDARD_PROVIDERS.map { it.key to it.value.provide(ctx) }.also { args.putAll(it.toMap()) }
        return args
    }


    companion object {

        val DEFAULT_PROVIDERS = mapOf(
            "date" to TranslationProvider { _ -> LocalDate.now().toString() },
            "time" to TranslationProvider { _ -> LocalTime.now().toString() },

        )

        val STANDARD_PROVIDERS = mutableMapOf<String, TranslationProvider>()
    }

}