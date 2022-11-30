package com.example.jetpackcomposedemo.components


import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ColorScheme

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

/**
 * 状态与组合
 * @Description:
 * @author: aking
 * @CreateDate: 2022/8/18 11:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/8/18 11:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HelloContext(){
    val keyboardController = LocalSoftwareKeyboardController.current
    // 旋转屏幕时数据会变成修改之前的状态
    var name by remember { mutableStateOf("") }
    // rememberSaveable可以保存数据状态，让屏幕旋转时数据仍旧不发生改变
    var isError by rememberSaveable {
        mutableStateOf(true)
    }
    println("name=${name},isError=${isError}")
    Column(modifier = Modifier.padding(16.dp)) {

        fun validate(text:String){

            isError = text.count()<8
            println("text=${text},isError=${isError}")
        }
        Text(text = "Hello =${name}", modifier = Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.h5)
        //带边框的输入框
        OutlinedTextField(value = name, //显示的文本内容
            onValueChange = {name = it
                             validate(name)

                            }, //输入数据变化后的回调
            singleLine = true,
            maxLines = 1,
            readOnly = false,
            enabled = true,
            isError = isError,
            keyboardActions= KeyboardActions {
                 validate(name)
                //隐藏当前的输入法
                keyboardController?.hide()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                //输入内容设置(Sentences:将输入的第一个字符大写)
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
                    //内容显示转变
            visualTransformation = PasswordVisualTransformation(),
            //输入框前面的图标
            leadingIcon = { Image(painter = painterResource(id = android.R.drawable.ic_input_add), contentDescription = "",Modifier.clickable {
                name = "add"
            })},
            //输入框后面的图标
            trailingIcon = { Image(painter = painterResource(id = android.R.drawable.ic_menu_report_image), contentDescription = "",Modifier.clickable {
                name ="delete 长度大于8位"
            })},
            //文本提示占位符 类似Tint的效果
            placeholder = { Text(text = "请输入搜索关键字", color = Color.DarkGray, fontSize = 12.sp)},
            label = { Text(text =if(isError) "长度不能少于8位" else "请输入密码")})
    }
}


/**
 * 状态下降、事件上升的这种模式称为“单向数据流”
 * 在这种情况下，状态会从 HelloScreen 下降为 HelloContent，
 * 事件会从 HelloContent 上升为 HelloScreen。
 * 通过遵循单向数据流，
 * 在界面中显示状态的可组合项与应用中存储和更改状态的部分解耦
 * @param name String
 * @param onNameChange Function1<String, Unit>
 */
@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }

    HelloContent(name = name, onNameChange = { name = it })
}


@Parcelize
data class City(val name:String,val country:String):Parcelable


val CitySaver = run {
    val nameKey = "name"
    val countryKey ="Country"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
           restore = {City(it[nameKey] as String,it[countryKey] as String)}

    )
}

val CityListSaver = listSaver<City, Any>(
    save = { listOf(it.name, it.country) },
    restore = { City(it[0] as String, it[1] as String) }
)
/**
 * Saver
 * Parcelize 保存状态数据
 */
@Composable
fun CityScreen(){
    val selectedCity = rememberSaveable {
        mutableStateOf(City("Madrid", "Spain"))
    }

    //MapSaver
    val selectedCity2 = rememberSaveable(stateSaver = CitySaver ) {
        mutableStateOf(City("Madrid", "Spain"))
    }

    //ListSaver
    val selectedCity3 = rememberSaveable(stateSaver = CityListSaver ) {
        mutableStateOf(City("Madrid", "Spain"))
    }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    

    Column(modifier = Modifier.padding(5.dp)) {
        Text(text = "city=${selectedCity.value.name}")
        Text(text = "country=${selectedCity.value.country}")
    }
}

@Composable
fun ScaffoldStateComposable(){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LocalContext.current
    Scaffold(scaffoldState = scaffoldState) {
        it.calculateBottomPadding()
        Content{
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("123")
            }
        }
    }
}

@Composable
fun Content(call:()->Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(text = "Content", color = MaterialTheme.colors.primary, modifier = Modifier.clickable {
            call.invoke()
        })
    }
}




@Preview
@Composable
fun DefaultPreview() {
    ScaffoldStateComposable()
}