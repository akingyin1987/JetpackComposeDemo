package com.example.jetpackcomposedemo


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TopBar(appTopBarTitle = "123")
                   // Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun TopBar(appTopBarTitle:String){
    //
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = appTopBarTitle, modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.CenterStart), color = Color.White
            )
        }, navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(context,"返回",Toast.LENGTH_SHORT).show()
            }) {

                Icon( Icons.Filled.ArrowBack, null, tint = Color.White )
            }
        }, modifier = Modifier.fillMaxWidth().height(48.dp)
       , actions = {
           //其它按钮 相当于是ToolBar 菜单 菜单

        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeDemoTheme(darkTheme = true) {
      //  Greeting("Android")
        TopBar(appTopBarTitle = "这是标题如果很长看要怎么显示")
    }
}