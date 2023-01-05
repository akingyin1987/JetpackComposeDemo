/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.countdown

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.ui.theme.Blue8A
import com.example.jetpackcomposedemo.ui.theme.bonava
import com.example.jetpackcomposedemo.viewmodel.CountdownTimerViewModel
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin


/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/4 14:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/4 14:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@Preview
@Composable
fun CountdownScreen(){
    val viewModel: CountdownTimerViewModel = viewModel()

    /** 通过状态改变动画 */
    val angle = animateFloatAsState(
        targetValue = if (viewModel.countdownSeconds == 0) 0f else 360f,
        TweenSpec(durationMillis = viewModel.countdownSeconds * 1000, easing = LinearEasing),
        finishedListener = {
            if (it == 360f) {
                viewModel.preStop()
            } else if (it == 0f) {
                viewModel.stop()
            }
        }
    )
    if(!viewModel.animateFinished){
        viewModel.animateNow()
    }
    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier.size(240.dp)
            .align(Alignment.Center)
            .padding(10.dp)
            .drawWithContent {
                //画圆
                drawCircle(
                    color = Color.White,
                    radius = 110.dp.toPx(),
                    style = Stroke(width = 5.dp.toPx())
                )
                val x = center.x + sin(
                    Math.toRadians(angle.value.toDouble()).toFloat()
                ) * 110.dp.toPx()

                val y = center.y - cos(
                    Math.toRadians(angle.value.toDouble())
                        .toFloat()
                ) * 110.dp.toPx()

                drawCircle(
                    color = Blue8A,
                    radius = 10.dp.toPx(),
                    center = Offset(x, y)
                )
                drawArc(
                    color = Blue8A,
                    startAngle = -90f,
                    sweepAngle = angle.value,
                    style = Stroke(width = 5.dp.toPx()),
                    useCenter = false
                )
//                //转换为原生Canvas
//                drawIntoCanvas { canvas->
//
//
//                }
                drawContent()
            }){
            Text(
                buildAnnotatedString {
                    val colorTime = Color.White
                    val seconds =
                        ceil((viewModel.countdownSeconds * (360 - angle.value) / 360).toDouble())
                            .toInt()
                    val hour = seconds / 3600
                    val minute = (seconds - hour * 3600) / 60
                    val second = seconds % 60
                    withStyle(style = SpanStyle(colorTime, fontSize = 36.sp)) {
                        append(String.format("%02d", hour))
                    }
                    withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)) {
                        append("h  ")
                    }
                    withStyle(style = SpanStyle(colorTime, fontSize = 36.sp)) {
                        append(String.format("%02d", minute))
                    }
                    withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)) {
                        append("m  ")
                    }
                    withStyle(style = SpanStyle(colorTime, fontSize = 36.sp)) {
                        append(String.format("%02d", second))
                    }
                    withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)) {
                        append("s")
                    }
                },
                fontFamily = bonava,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}