package com.sonyassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sonyassignment.ui.theme.SonyAssignmentTheme

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SonyAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun HomeScreen() {
    val sizeMapData = arrayOf("English", "Hindi", "Chinease")
    Scaffold(
        bottomBar = {
            Box {
                val height = 54.dp * ((sizeMapData.size / 3) + 1)
                val selectedHeight = remember { mutableStateOf(height) }
                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    modifier = Modifier.height(selectedHeight.value)
                ) {
                    items(sizeMapData.size) { index ->
                        Button(modifier = Modifier.wrapContentWidth(), onClick = {}) {
                            Text(text = sizeMapData[index])
                        }
                    }
                }
            }
        },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Hello!")
        }
    }
}