package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/6 12:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/6 12:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstrainCompose(){
    Column(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
           .padding( 20.dp)
               ) {
            //通过createRefs创建二个引用
            val (inputVerifyCode,sendVerifyCode)=createRefs()
            //绑定引用 ,linkTo函数将view与其他view或者parent约束
            OutlinedTextField(modifier = Modifier.constrainAs(inputVerifyCode){
                absoluteLeft.linkTo(parent.absoluteLeft)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                absoluteRight.linkTo(sendVerifyCode.absoluteLeft)}, value = "123", onValueChange ={} , enabled = true, readOnly = false, label = {
                Text(text = "验证码")
            }, singleLine = true,
                maxLines = 1,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = "", tint =  MaterialTheme.colorScheme.primary)

                },
                trailingIcon = {
                    //右边框的图标
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "", tint = Color.Black, modifier = Modifier.clickable {

                    })
                },
                keyboardOptions =  KeyboardOptions( keyboardType = KeyboardType.Text)
            )

            Button(onClick = {

            }, modifier = Modifier
                .constrainAs(sendVerifyCode){

                    top.linkTo(inputVerifyCode.top, margin = 8.dp)
                    bottom.linkTo(inputVerifyCode.bottom)
                    absoluteLeft.linkTo(inputVerifyCode.absoluteRight)
                    absoluteRight.linkTo(parent.absoluteRight)
                }
                , shape = RoundedCornerShape(3.dp)) {
                Text(text =  "验证码", modifier = Modifier.align(
                    Alignment.CenterVertically))
            }
        }
    }

}


@Preview
@Composable
fun ConstrainComposeScreenPreview(){
    JetpackComposeDemoTheme {
        ConstrainCompose()
    }
}