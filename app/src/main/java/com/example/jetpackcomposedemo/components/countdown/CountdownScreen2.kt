/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.countdown

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.components.countdown.HMSFontInfo.Companion.HMS
import com.example.jetpackcomposedemo.components.countdown.HMSFontInfo.Companion.MS
import com.example.jetpackcomposedemo.components.countdown.HMSFontInfo.Companion.S
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.example.jetpackcomposedemo.ui.theme.purple200
import com.example.jetpackcomposedemo.ui.theme.teal200

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/4 16:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/4 16:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Composable
fun CountdownScreen1(timeInSec:Int,onCancel:()->Unit){

    var trigger by remember { mutableStateOf(timeInSec) }

    /** 状态动画-处理倒计时 */
    val elapsed by animateIntAsState(
        /** 目标值 */
        targetValue = trigger * 1000,
        /** 动画规格 */
        animationSpec = tween(durationMillis = timeInSec * 1000, easing = LinearEasing),
        finishedListener = {
            println("finishedListener=${it}")
        }
    )

    DisposableEffect(Unit){
        trigger = 0
        onDispose {  }
    }
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(start = 10.dp, end = 10.dp)) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Box {
            println("elapsed=${elapsed}")
            AnimationElapsedTime(elapsed)
            AnimationCircleCanvas(elapsed)
        }
        Spacer(modifier = Modifier.size(55.dp))

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(70.dp)
                .shadow(30.dp, shape = CircleShape)
                .clickable { onCancel() },
            imageVector = Icons.Default.Cancel,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}


/**
 * 将秒转化为时间格式显示
 * @receiver BoxScope
 * @param elapsed Int
 */
@Composable
private fun BoxScope.AnimationElapsedTime(elapsed:Int){
    val (hou, min, sec) = remember(elapsed / 1000) {
        val elapsedInSec = elapsed / 1000
        val hou = elapsedInSec / 3600
        val min = elapsedInSec / 60 - hou * 60
        val sec = elapsedInSec % 60
        Triple(hou, min, sec)
    }

    val mills = remember(elapsed) {
        elapsed % 1000
    }

    val onlySec = remember(hou, min) {
        hou == 0 && min == 0
    }


    /** 使用 rememberInfiniteTransition 实现动画无限制执行效果 */
    val transition = rememberInfiniteTransition()

    /** infiniteRepeatable+reverse实现文字缩放 */
    val animatedFont by transition.animateFloat(
        initialValue = 1.5f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    /** 根据时间 格式 提供了三种不同的尺寸  */
    val (size, labelSize, padding) = when {
        hou > 0 -> HMS
        min > 0 -> MS
        else -> S
    }

    Row(modifier = Modifier
        .align(Alignment.Center)
        .padding(start = padding, end = padding, top = 10.dp, bottom = 10.dp)) {
        if(hou>0){
            DisplayTime(hou.formatTime(),"h", fontSize = size, labelSize = labelSize )
        }
        if(min>0){
            DisplayTime(min.formatTime(),"m",fontSize = size, labelSize = labelSize )
        }
        DisplayTime(
            num = if(onlySec) sec.toString() else sec.formatTime(),
            label = if(onlySec) "" else "s",
            fontSize = size * (if(onlySec && sec < 10 && mills != 0) animatedFont else 1f),
            labelSize = labelSize,
            textAlign = if(onlySec) TextAlign.Center else TextAlign.End
        )
    }
    Text(
        text = ".${(mills/10).formatTime()}",
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 80.dp),
        fontSize = 30.sp,
        fontFamily = FontFamily.Cursive,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary

    )

}


@Composable
fun BoxScope.AnimationCircleCanvas(durationMills: Int){
    val transition = rememberInfiniteTransition()
    var trigger by remember { mutableStateOf(0f) }
    var isFinished by remember { mutableStateOf(false) }

    /** 线形雷达动画 */
    val animateTween by animateFloatAsState(
        targetValue = trigger,
        animationSpec = tween(
            durationMillis = durationMills,
            easing = LinearEasing
        ),
        finishedListener = {
            isFinished = true
        }
    )

    /** infiniteRepeatable+restart实现跑马灯 */
    val animatedRestart by transition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(tween(2000),RepeatMode.Restart)
    )


      //infiniteRepeatable+reverse实现呼吸灯
    val animatedReverse by transition.animateFloat(
        initialValue = 1.05f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse)
    )

    DisposableEffect(Unit) {
        trigger = 360f
        onDispose {}
    }
    val strokeRestart = Stroke(15f)
    val strokeReverse = Stroke(10f)
    val color = MaterialTheme.colorScheme.primary
    val secondColor = MaterialTheme.colorScheme.secondary
    Canvas(modifier = Modifier
        .align(Alignment.Center)
        .padding(16.dp)
        .size(350.dp) ){
        val diameter = size.minDimension
        val radius = diameter / 2f
        val topLeftOffset = Offset(10f, 10f)
        val size = Size(radius * 2, radius * 2)
        if(!isFinished){
            /** 跑马灯半圆 */
            drawArc(
                color = color,
                startAngle = animatedRestart,
                sweepAngle = 150f,
                topLeft = topLeftOffset,
                size = size,
                useCenter = false,
                style = strokeRestart,
            )
        }
        /** 呼吸灯整圆 */
        drawCircle(
            color = secondColor,
            style = strokeReverse,
            radius = radius * if (isFinished) 1f else animatedReverse
        )
        /** 雷达扇形 */
        drawArc(
            startAngle = 270f,
            sweepAngle = animateTween,
            brush = Brush.radialGradient(
                radius = radius,
                colors = listOf(
                    purple200.copy(0.3f),
                    teal200.copy(0.2f),
                    Color.White.copy(0.3f)
                ),
            ),
            useCenter = true,
            style = Fill,
        )
    }

}


@Preview(showBackground = true)
@Composable
fun DisplayPreview() {
    JetpackComposeDemoTheme(darkTheme = true) {
        CountdownScreen1(100) {}
    }

}

private fun Int.formatTime() = String.format("%02d", this)

private data class HMSFontInfo(val fontSize: TextUnit, val labelSize: TextUnit, val padding: Dp) {
    companion object {
        val HMS = HMSFontInfo(50.sp, 20.sp, 40.dp)
        val MS = HMSFontInfo(85.sp, 30.sp, 50.dp)
        val S = HMSFontInfo(150.sp, 50.sp, 55.dp)
    }
}