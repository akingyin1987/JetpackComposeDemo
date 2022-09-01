package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/1 11:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/1 11:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginCompose(exitCall:()->Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
    //当前输入账号
    var account by rememberSaveable{
        mutableStateOf("account")
    }

    var pwd  by remember {
        mutableStateOf("")
    }

    //0=正常登录 1=手机号登录
    val  loginModel = rememberSaveable {
        mutableStateOf(0)
    }
   //是否密码显示
    var isVisible by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(3.dp)) {
        Row(Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                exitCall.invoke()
            }) {
                Icon(imageVector = Icons.Filled.ExitToApp , contentDescription ="", modifier = Modifier.padding(3.dp), tint =   MaterialTheme.colorScheme.onSurface)
            }

            Spacer(modifier = Modifier.weight(1F))
            
            IconButton(onClick = {
                exitCall.invoke()
            }) {
                Icon(imageVector = Icons.Filled.Settings , contentDescription ="" , modifier = Modifier.padding(3.dp), tint = MaterialTheme.colorScheme.onSurface )
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
        Text(text = "业务标准化运行平台",
            Modifier
                .padding(3.dp)
                .fillMaxWidth(),textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 32.sp)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            //设置水平居中对齐
            horizontalArrangement = Arrangement.Center,
            //设置垂直居中对齐
            verticalAlignment = Alignment.CenterVertically) {

           Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "")
            Text(text = if(loginModel.value == 0) ".用户账号密码登录" else ".手机短信登录", color = MaterialTheme.colorScheme.primaryContainer)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {

           OutlinedTextField(modifier = Modifier.fillMaxWidth(), maxLines = 1, value = account, onValueChange ={account = it} , enabled = true, readOnly = false, label = {
               Text(text = "用户账号")
           }, singleLine = true,
               placeholder = {
                     //输入框的提示语类似hint
                     Text(text = "请输入登录账号")
               }, leadingIcon = {
                   //左边框的图标
                   Icon(imageVector = Icons.Default.AccountBox, contentDescription = "", tint = if(account.isEmpty()) Color.Black else MaterialTheme.colorScheme.primary)
               }
               , trailingIcon = {
                  //右边框的图标
                   if(account.isNotEmpty()){
                       Icon(imageVector = Icons.Default.Clear, contentDescription = "", tint = Color.Black, modifier = Modifier.clickable {
                           account =""
                       })
                   }

               }
               ,keyboardActions = KeyboardActions(onGo = {
                   //设置点击动作监听
                   keyboardController?.hide()
               }),
               //设置键盘参数
               keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go, keyboardType = KeyboardType.Text))
        }

        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()) {

            OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = pwd, onValueChange ={pwd = it} , enabled = true, readOnly = false, label = {
                Text(text = "密码")
            }, singleLine = true,
                maxLines = 1,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Password, contentDescription = "", tint = if(pwd.isEmpty()) Color.Black else MaterialTheme.colorScheme.primary)

                },
                trailingIcon = {
                    Icon(imageVector = if(isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, contentDescription = "", modifier = Modifier.clickable {
                        isVisible = !isVisible
                    })
                },
                visualTransformation = if(isVisible) VisualTransformation.None else PasswordVisualTransformation())
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { }, modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(3.dp), enabled = account.isNotEmpty() && pwd.isNotEmpty()) {

            Text(text = "登录")
        }
    }
}


@Preview
@Composable
fun DefaultPreview5(){
    JetpackComposeDemoTheme{
        LoginCompose{

        }
    }

}