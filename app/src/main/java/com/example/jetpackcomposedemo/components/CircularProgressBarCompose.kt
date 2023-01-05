/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *  圆形进度条
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/4 13:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/4 13:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Composable
fun CircularProgressBar(
    percentage : Float,
    number : Int,
    fontSize : TextUnit = 28.sp,
    radius : Dp = 60.dp,
    color: Color = Color.Green,
    strokeWidth : Dp = 8.dp,
    animDuration : Int = 1000,
    animDelay : Int = 0
){
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true){
        animationPlayed = true

    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2.2f)
    ){
        Canvas(modifier = Modifier.size(radius * 2f)){
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

