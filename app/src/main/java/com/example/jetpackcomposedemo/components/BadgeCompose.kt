/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 标记
 * @Description:
 * @author: aking
 * @CreateDate: 2022/11/30 9:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/11/30 9:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BadgeComposePreview(){
    Column(modifier = Modifier.fillMaxSize()) {
       Badge{
           Text(text = "123")
       }
       Badge(containerColor = Color.Green, contentColor = Color.Black){
           Text(text = "321")
       } 
       BadgedBox(badge = {
           Badge{
               Text(text = "123")
           }
       }, modifier = Modifier.wrapContentWidth().wrapContentHeight()) {
           Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(4.dp)) {
               Text(text = "这是按钮")
           }
       } 
    }
}