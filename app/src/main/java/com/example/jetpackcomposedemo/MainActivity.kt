package com.example.jetpackcomposedemo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.components.*
import com.example.jetpackcomposedemo.databinding.ActivityTestAndroidViewBinding
import com.example.jetpackcomposedemo.ui.theme.ComposeCookBookTheme

import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.example.jetpackcomposedemo.viewmodel.EditViewModel

/**
 * 声明式编程 它描述目标的性质，让计算机明白目标，而非流程
 * 声明式编程是告诉计算机需要计算“什么”而不是“如何”去计算
  任何没有副作用的编程语言，或者更确切一点，任何引用透明的编程语言
   任何有严格计算逻辑的编程语言
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDemoTheme {
                val editViewModel:EditViewModel = viewModel()

                AndroidViewBinding(factory = { inflater, parent, attachToParent->
                    ActivityTestAndroidViewBinding.inflate(inflater,parent,attachToParent).also {
                        it.viewModel = editViewModel
                        it.lifecycleOwner = this
                    }
                }, update = {

                })
              //  HomeScreenCompose()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun TopBar(appTopBarTitle:String,call:()->Unit){
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
        }, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
       , actions = {
           //其它按钮 相当于是ToolBar 菜单 菜单

        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeDemoTheme(darkTheme = true) {
        RowArtistCard()
       // ScaffoldCompose()
//        Column() {
//            TopBar(appTopBarTitle = "这是标题"){
//
//            }
//            HelloContext()
//        }
      //  Greeting("Android")

    }
}