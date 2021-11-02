package com.sonyassignment

import com.opencsv.CSVReader
import com.sonyassignment.webservice.FILE_NAME
import java.io.File
import java.io.FileReader

// file is stored at https://filebin.net/trpjj0svhw3u2cui
fun prepTranslationArray(path: String): ArrayList<LanguageModel>? {
    val dataList = ArrayList<LanguageModel>()
    try {
        val file = File(path, FILE_NAME)
        val reader =
            CSVReader(FileReader(file.path)) //Specify asset file name
        var nextLine: Array<String>? = null

        val row = reader.readNext()
        val list = ArrayList<String>()
        for (i in 2 until row.size) {
            list.add(row[i])
        }
        dataList.add(LanguageModel(row[0], row[1], list))
        while (reader.readNext().also { nextLine = it } != null) {
            val tmpList = ArrayList<String>()
            nextLine?.let {
                for (i in 2 until it.size) {
                    tmpList.add(nextLine?.get(i) ?: "")
                }
                dataList.add(LanguageModel(nextLine?.get(0), nextLine?.get(1), tmpList))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null

    }
    return dataList
}