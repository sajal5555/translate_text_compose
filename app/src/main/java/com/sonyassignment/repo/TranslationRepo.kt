package com.sonyassignment.repo

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.sonyassignment.webservice.FILE_NAME
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
    var response = mutableStateOf<String?>(null)

    fun downloadFile(path: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = translationDataSource.downloadFile()
            data.collect {
                response.value = saveFile(it, path)
            }

        }
    }


    private fun saveFile(body: ResponseBody?, path: String): String? {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            val yourFile = File(path, FILE_NAME)
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
            return null
        } finally {
            input?.close()
        }
        return null
    }
}