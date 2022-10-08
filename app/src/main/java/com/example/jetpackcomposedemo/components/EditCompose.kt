package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.viewmodel.EditViewModel
import com.example.jetpackcomposedemo.vo.User

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/9 10:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/9 10:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Composable
fun EditCompose(viewModel: EditViewModel = viewModel(), call: () -> Unit) {

    val user = viewModel.userLiveData.observeAsState(initial = User())


    val userState = rememberUserState()


    var account by remember {
        mutableStateOf("")
    }

    val pass by remember {
        mutableStateOf(user.value)
    }

//    val user by remember {
//        mutableStateOf(User())
//    }

    println("user.name=${userState.value.name}")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        OutlinedTextField(
            value = userState.value.name,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                userState.value.name = it
                userState.value=userState.value

            },
            label = {
                Text(text = "输入账号")
            })


        EditView(text = user.value.passWord) { pass ->
            viewModel.onChangePassWord(pass)
            println(user.value.passWord)
        }

        Button(onClick = {
            println("userState.value=${userState.value.name}")
            call.invoke()
        }, content = { Text(text = "登录") }, modifier = Modifier.fillMaxWidth())
    }


}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditView(text: String, onTextChange: (String) -> Unit) {
    val keyboard  =  LocalSoftwareKeyboardController.current
    keyboard?.show()

    OutlinedTextField(value = text, modifier = Modifier.fillMaxWidth(), onValueChange = {
        onTextChange.invoke(it)

    }, label = {
        Text(text = "输入密码")
    })
}

@Composable
fun EditTextView(){
    TextField(value = "", onValueChange = {}, maxLines = 2, keyboardOptions = KeyboardOptions(), keyboardActions = KeyboardActions())
}

@Composable
fun rememberUserState(myUserStatus: MutableState<User> = mutableStateOf(User())) =
    remember(key1 = myUserStatus.value.version) {
        //改变策略
        // referentialEqualityPolicy 引用相等
        // structuralEqualityPolicy 数值相等策略
        // neverEqualPolicy 从不相等策略
        mutableStateOf(User(myUserStatus.value.name, myUserStatus.value.passWord), policy = neverEqualPolicy())
    }