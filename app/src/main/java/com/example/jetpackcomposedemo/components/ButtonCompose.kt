/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/11/24 22:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/11/24 22:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
//@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ButtonComposePreview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { }) {
            Text(text = "button")
        }



        OutlinedButton(
            onClick = { },
            shape = CircleShape,
            border = BorderStroke(5.dp, Color.Green),
            modifier = Modifier.size(34.dp),
            contentPadding = PaddingValues(1.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.Red
            )
        ) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = null)
        }

        OutlinedButton(
            onClick = { },
            shape = CircleShape,
            border = BorderStroke(5.dp, Color.Green),
            modifier = Modifier.size(50.dp),
            contentPadding = PaddingValues(1.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.Red
            )
        ) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = null)
        }


    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .border(3.dp, Color.Black), propagateMinConstraints = false
    ) {
        var offset by remember {
            mutableStateOf(Offset.Zero)
        }
        OutlinedIconButton(
            onClick = {
                scope.launch {
                    snackBarHostState.showSnackbar(message = "点击事件")
                }

            },
            shape = CircleShape,
            border = BorderStroke(5.dp, Color.Green),

            modifier = Modifier
                .size(50.dp)
                .offset {
                    IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            //开始拖动
                        }, onDrag = { change, dragAmount ->
                            //拖动中
                            offset += dragAmount
                        }, onDragEnd = {
                            //拖动结果
                            if (offset.x < 0) {
                                offset = offset.copy(0F, offset.y)
                            }
                            if (offset.y < 0) {
                                offset = offset.copy(offset.x, 0F)
                            }

                        }, onDragCancel = {
                            //拖动取消
                        })
                },
        ) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = null)
        }
    }
}


@Preview(name = "dark 1", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "light 1", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ButtonComposePreview2() {

    JetpackComposeDemoTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val enable  = remember { mutableStateOf(true) }
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    enable.value = !enable.value
                }) {
                    Text(text = "button set enable")
                }
                Button(onClick = {  }, enabled = enable.value) {
                    Text(text = "button")
                }

                OutlinedButton(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = ContentAlpha.high)),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    contentPadding = PaddingValues(0.dp),
                    border = BorderStroke(1.dp, Color("#CCCCCCCC".toColorInt())),
                    enabled = enable.value

                ) {
                    Text(text = "OutlinedButton")
                }

                ElevatedButton(onClick = { /*TODO*/ },enabled = enable.value) {
                    Text(text = "ElevatedButton")
                }

                FilledTonalButton(onClick = { /*TODO*/ },enabled = enable.value) {
                    Text(text = "FilledTonalButton")
                }
                TextButton(onClick = { /*TODO*/ },enabled = enable.value) {
                    Text(text ="TextButton" )
                }
                IconButton(onClick = {  }, enabled = enable.value) {
                    Icon(imageVector = Icons.Default.Work, contentDescription = null)
                }

                IconToggleButton(checked = enable.value, onCheckedChange ={} ) {
                    if(enable.value){
                        Icon(imageVector = Icons.Default.Work, contentDescription = null)
                    }else{
                        Icon(imageVector = Icons.Default.WorkOff, contentDescription = null)
                    }
                }
                FilledIconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Work, contentDescription = null)
                }

                val checkState = remember {
                    mutableStateOf(false)
                }
                OutlinedIconToggleButton(checked = checkState.value, onCheckedChange ={
                    checkState.value = it
                } ) {
                    Row {
                       Text(text = "已解决", color = if(enable.value) Color.Green else Color.Unspecified)
                       Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null, tint = if(enable.value) Color.Green else Color.Unspecified)
                    }
                }
            }
        }
    }
}