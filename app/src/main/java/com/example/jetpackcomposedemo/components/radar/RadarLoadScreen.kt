/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.radar

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.example.jetpackcomposedemo.ui.theme.purple200
import com.example.jetpackcomposedemo.ui.theme.teal200
import kotlin.math.min

/**
 * 雷达加载扫描图
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/5 14:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/5 14:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Composable
fun RadarLoadScreen(modifier: Modifier){

    //循环动画
    val trans = rememberInfiniteTransition()


    val rote by trans.animateFloat(
        initialValue = 0F,
        targetValue =360F ,
        animationSpec = infiniteRepeatable(
            animation = tween(5000,0, LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier){
        val strokeWidth = 3.dp.toPx()
        val realSize  = min(size.width,size.height)/2-strokeWidth

        //画圆
        drawCircle(
            color = Color.White,
            radius = realSize,
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            startAngle = 0F,
            sweepAngle = rote,
            useCenter = true,
            style = Fill,
            /** 环形渐变 */
            brush = Brush.radialGradient(
                radius = realSize - strokeWidth,
                colors = listOf(
                    purple200.copy(0.3f),
                    teal200.copy(0.2f),
                    Color.White.copy(0.3f)
                )
            )
        )

    }


}

@Preview(showBackground = true)
@Composable
fun RadarLoadScreenPreview(){
    JetpackComposeDemoTheme(darkTheme = true) {
        RadarLoadScreen(
            Modifier
                .size(200.dp)
                )
    }

}