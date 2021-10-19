package com.akshatsahijpal.crud.util

import android.net.Uri
import androidx.core.net.toUri
import java.io.File

fun interface Converter<T, U> {
    fun convert(t: T): U
}

internal object FileToUriConverter : Converter<File, Uri> {
    override fun convert(t: File): Uri = t.toUri()
}

fun fileToUriConverter(): Converter<File, Uri> = FileToUriConverter