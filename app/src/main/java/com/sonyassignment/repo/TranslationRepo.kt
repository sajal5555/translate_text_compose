package com.sonyassignment.repo

import android.os.Environment
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.getKoin
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class TranslationRepo(
    private val translationDataSource: TranslationDataSource = getKoin().get(),
) {

    // file is stored at https://filebin.net/trpjj0svhw3u2cui
    val fileName = "TranslationFile.csv"

    fun downloadFile() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = translationDataSource.downloadFile()
            data.collect {
                saveFile(it, fileName)
            }
        }
    }


    private fun saveFile(body: ResponseBody?, fileName: String): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            val yourFile = File(Environment.getDataDirectory(), fileName)
            yourFile.createNewFile() // if file already exists will do nothing

            val fos = FileOutputStream(yourFile, false)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return yourFile.path
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            input?.close()
        }
        return ""
    }
}