/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.animation

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/7 13:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/7 13:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Preview
@Composable
fun SpringAnimationScreen(){
    val springSpec =
        SpringSpec(dampingRatio = 0.1f,
            stiffness = 500f,
            visibilityThreshold = 0.01.dp)
   var defaultTop  by remember { mutableStateOf(10.dp) }


   var number by remember { mutableStateOf(0) }
   LaunchedEffect(number){
       if(number %2==0){
           defaultTop = 20.dp
       }else{
           defaultTop = 10.dp
       }

   }
   val paddingTop by animateDpAsState(defaultTop, animationSpec = springSpec)
    Box(modifier = Modifier
        .fillMaxSize()
        .border(2.dp, Color.Black)){

        Icon(imageVector = Icons.Default.Circle, contentDescription = null, modifier = Modifier
            .padding(top = paddingTop)
          )
        Button(onClick = {
            number++
        }, modifier = Modifier.padding(top = 100.dp).fillMaxWidth()) {
            Text(text = "计数=${number}")
        }
    }
}