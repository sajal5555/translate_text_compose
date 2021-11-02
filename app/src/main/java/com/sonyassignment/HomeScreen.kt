package com.sonyassignment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.opencsv.CSVReader
import com.sonyassignment.repo.TranslationRepo
import com.sonyassignment.webservice.FILE_NAME
import org.koin.java.KoinJavaComponent.getKoin
import java.io.File
import java.io.FileReader


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    translationRepo: TranslationRepo = getKoin().get()
) {
    val context = LocalContext.current
    val path = context.getExternalFilesDir(null)?.absolutePath ?: ""
    val dataList = remember {
        mutableStateOf<ArrayList<LanguageModel>?>(null)
    }
    val selectedCode = remember {
        mutableStateOf("")
    }
    Scaffold(
        bottomBar = {
            Box {
                dataList.value?.let {
                    val height = 54.dp * ((it.size / 3) + 1)
                    val selectedHeight = remember { mutableStateOf(height) }
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(3),
                        modifier = Modifier.height(selectedHeight.value)
                    ) {
                        items(it.size) { index ->
                            Box(
                                modifier = Modifier.padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(modifier = Modifier.wrapContentWidth(),
                                    onClick = {
                                        selectedCode.value = it[index].languageCode ?: ""
                                    }) {
                                    Text(text = it[index].language ?: "")
                                }
                            }
                        }
                    }
                }
            }
        },
    ) {
        LaunchedEffect(true) {
            translationRepo.downloadFile(path)
        }
        if (translationRepo.response.value?.isNotBlank() == true) {
            if (File(path, FILE_NAME).exists()) {
                dataList.value = prepTranslationArray(path)
            }
            translationRepo.response.value = null
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            if (selectedCode.value.isNotBlank()) {
                val selectedLanguage =
                    dataList.value?.filter { it.languageCode == selectedCode.value }?.get(0)

                Column {
                    selectedLanguage?.texts?.forEach {
                        Text(text = it)
                    }
                }
            }
        }
    }
}


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