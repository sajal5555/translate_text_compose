package com.sonyassignment.repo

import android.os.Environment
import android.util.Log
import com.sonyassignment.webservice.RepoResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.koin.core.Koin
import java.io.FileOutputStream
import java.io.InputStream

class TranslationRepo(
    private val translationDataSource: TranslationDataSource = Koin().get(),
) {
    // file is stored at https://filebin.net/trpjj0svhw3u2cui
    val fileUrl = "https://filebin.net/trpjj0svhw3u2cui/TranslationFile.csv"
    val fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
    val pathWhereYouWantToSaveFile = Environment.getDataDirectory().absolutePath + fileName

     fun downloadFile() {
        CoroutineScope(Dispatchers.IO).launch {
            translationDataSource.downloadFile(fileUrl).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is RepoResult.Success -> {
                            saveFile(it as ResponseBody, pathWhereYouWantToSaveFile)
                        }
                        is RepoResult.Error -> {

                        }
                    }
                }
            }
        }
    }


    private fun saveFile(body: ResponseBody?, pathWhereYouWantToSaveFile: String): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            //val file = File(getCacheDir(), "cacheFileAppeal.srl")
            val fos = FileOutputStream(pathWhereYouWantToSaveFile)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            input?.close()
        }
        return ""
    }
}