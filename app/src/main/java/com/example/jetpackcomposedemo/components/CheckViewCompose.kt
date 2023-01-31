/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*


import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/29 12:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/29 12:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@Preview
@Composable
fun CheckViewComposePreview(){
  JetpackComposeDemoTheme {
      var checkState by remember {
          mutableStateOf(false)
      }
      Column(modifier = Modifier.fillMaxSize()) {
          Checkbox(checked = checkState , onCheckedChange = {
              checkState = it
          })
          Row {

          }
      }
  }
}


@Composable
fun CheckBoxItem(modifier: Modifier,checkValue:Boolean,title:String,onCheckedChange:(Boolean)->Unit){
    Checkbox(modifier = modifier, checked = checkValue , onCheckedChange = onCheckedChange)
    Text(text = title, fontSize = 15.sp, modifier = Modifier.offset(5.dp))
}