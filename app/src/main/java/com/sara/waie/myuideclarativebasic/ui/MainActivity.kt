package com.sara.waie.myuideclarativebasic.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sara.waie.myuideclarativebasic.ui.theme.MyUiDeclarativeBasicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme

                    Greeting1("Android")


        }
    }
}

fun test(){
    MainScope().launch() {

    }
}

@Composable
fun Greeting1(name: String) {
    Text(text = "Hello $name!",modifier = Modifier.fillMaxHeight())
}
@Composable
fun myTextView(){
    Text(text = buildString {

    })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyUiDeclarativeBasicTheme {
        Column{
            Greeting1("Anidaaa")
            Row {
                Greeting1("Anidaaa")
                Greeting1("Anidaaa")
            }
            Greeting1("Anidaaa")
        }

    }
}