package com.akshatsahijpal.crud

import androidx.core.net.toFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.akshatsahijpal.crud.util.fileToUriConverter
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class FileToUriConverterTest {

    @Test
    fun convertFileToUri() {
        val converter = fileToUriConverter()
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertNotNull(appContext)
        assertNotNull(appContext.cacheDir.path)
        val file = File(appContext.cacheDir.path)
        val uri = converter.convert(file)
        assertEquals(uri.toFile(), file)
    }
}