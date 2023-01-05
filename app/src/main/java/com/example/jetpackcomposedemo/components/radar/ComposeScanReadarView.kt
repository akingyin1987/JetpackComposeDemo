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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.jetpackcomposedemo.ui.theme.purple200
import com.example.jetpackcomposedemo.ui.theme.teal200
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/5 12:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/5 12:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


/**
 * 雷达扫描
 * @param modifier Modifier
 */
@Composable
fun ComposeScanRadarView(
    modifier: Modifier = Modifier,
    color: Color = Color.Green,
    strokeWidth: Float = 10f,
) {
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
        //画三个圆
        val realSize  = min(size.width,size.height)
        drawCircle(color,realSize/6, style = Stroke(strokeWidth))
        drawCircle(color,realSize/3, style = Stroke(strokeWidth))
        drawCircle(color,realSize/2, style = Stroke(strokeWidth))

        //画十字架
        drawLine(
            color = color,
            start = Offset(size.width/2,(size.height-realSize)/2),
            end = Offset(size.width/2,(size.height+realSize)/2),
            strokeWidth = strokeWidth)
        drawLine(
            color = color,
            start = Offset((size.width-realSize)/2,size.height/2),
            end = Offset((size.width+realSize)/2,size.height/2),
            strokeWidth = strokeWidth)
        

    }
    Canvas(modifier = modifier.rotate(rote) ){
        val realSize  = min(size.width,size.height)

        //扫描视图
        drawCircle(
            brush = Brush.sweepGradient(0F to Color.Transparent,0.05F to Color.Green,0.051F to Color.Transparent),
            radius = realSize/2
        )
    }

    Canvas(modifier = modifier){

        val realSize  = max(size.width,size.height)
        drawArc(
            startAngle = 0f,
            sweepAngle = rote,
            brush = Brush.radialGradient(
                radius = realSize,
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


@Preview
@Composable
fun ComposeScanRadarPreview(){
    ComposeScanRadarView(modifier = Modifier.size(200.dp).padding(5.dp), strokeWidth = 5F)
}