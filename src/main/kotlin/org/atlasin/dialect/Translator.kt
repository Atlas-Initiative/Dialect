package org.atlasin.dialect

import fluent.bundle.FluentBundle
import fluent.functions.cldr.CLDRFunctionFactory
import fluent.syntax.parser.FTLParser
import fluent.syntax.parser.FTLStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.atlasin.inspekt.FileWatchEvent
import org.atlasin.inspekt.watchFile
import org.atlasin.inspekt.watcher
import java.io.File
import java.util.Locale

class Translator(private val resourceFile: File, val locale: Locale = Locale.US) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            watchFile(resourceFile.parentFile).consumeEach { event ->
                if(event.file.path == resourceFile.path) {
                    if(event.type == FileWatchEvent.Type.MODIFY) {
                        resourceBundle = loadBundle()
                    }
                }
            }
        }
    }

    private fun loadBundle() : FluentBundle {
        return FluentBundle.builder(locale, CLDRFunctionFactory.INSTANCE)
            .addResource(FTLParser.parse(FTLStream.of(resourceFile.readText()))).build()
    }

    private var resourceBundle = loadBundle()


    fun getTranslation(key : String, block : Translation.() -> Unit) : Translation {
        return Translation(resourceBundle, key).apply(block)
    }





}