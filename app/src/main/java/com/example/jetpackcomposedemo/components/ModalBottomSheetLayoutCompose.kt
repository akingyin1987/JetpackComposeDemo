/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import kotlinx.coroutines.launch

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/11/30 9:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/11/30 9:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheetLayoutPreview() {
    JetpackComposeDemoTheme {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = "测试")
            }, modifier = Modifier.shadow(4.dp),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                })
        }) {
           Column(modifier = Modifier
               .fillMaxSize()
               .padding(it)) {

               val bottomSheetState = rememberModalBottomSheetState(initialValue =ModalBottomSheetValue.Hidden, confirmStateChange = {sheetValue->
                   if(sheetValue == ModalBottomSheetValue.Hidden){

                   }
                   true
               })

               val scope  = rememberCoroutineScope()
               Button(onClick = {
                   if(bottomSheetState.isVisible){
                      scope.launch {
                          bottomSheetState.hide()
                      }
                   }else{
                       scope.launch {
                           bottomSheetState.show()
                       }
                   }
               }) {
                   Text(text = "显示ModalBottomSheetLayout")
               }
               ModalBottomSheetLayout(sheetContent = {
                   Column{
                       ListItem(
                           text = { Text("选择分享到哪里吧~") }
                       )

                       ListItem(
                           text = { Text("github") },
                           icon = {
                               Surface(
                                   shape = CircleShape,
                                   color = Color(0xFF181717)
                               ) {
                                   Icon(
                                      Icons.Default.Chat,
                                       null,
                                       tint = Color.White,
                                       modifier = Modifier.padding(4.dp)
                                   )
                               }
                           },
                           modifier = Modifier.clickable {  }
                       )

                       ListItem(
                           text = { Text("微信") },
                           icon = {
                               Surface(
                                   shape = CircleShape,
                                   color = Color(0xFF07C160)
                               ) {
                                   Icon(
                                       Icons.Default.Chat,
                                       null,
                                       tint = Color.White,
                                       modifier = Modifier.padding(4.dp)
                                   )
                               }
                           },
                           modifier = Modifier.clickable {  }
                       )
                      ListItem(headlineText = {
                          Text(text = "简易标定", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                      }, supportingText = {
                          Text(text = "简易标签详情", style = MaterialTheme.typography.bodySmall.copy(color = androidx.compose.material3.LocalContentColor.current.copy(alpha = ContentAlpha.disabled)))
                      }, leadingContent = {
                          Icon(imageVector = Icons.Default.TagFaces, contentDescription = null)
                      }, trailingContent = {
                          Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
                      })
                   }
               }, sheetState = bottomSheetState) {
               }


           }
        }
    }

}