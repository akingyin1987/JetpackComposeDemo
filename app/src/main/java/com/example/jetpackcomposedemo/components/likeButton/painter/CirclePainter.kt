/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.likeButton.painter

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/8 13:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/8 13:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.components.likeButton.CircleColor
import com.example.jetpackcomposedemo.components.likeButton.mapValueFromRangeToRange


fun DrawScope.drawLikeCircle(
    outerCircleRadiusProgress: Float,
    innerCircleRadiusProgress: Float,
    size: Dp,
    circleColor: CircleColor = CircleColor(
        start = Color(0xFFFF5722),
        end = Color(0xFFFFC107),
    )
) {

    val color = outerCircleRadiusProgress.coerceIn(
        minimumValue = 0.5f, maximumValue = 1.0f
    ).let {
        mapValueFromRangeToRange(
            value = it,
            fromLow = 0.5f,
            fromHigh = 1.0f,
            toLow = 0.0f,
            toHigh = 1.0f,
        )
    }.let {
        lerp(
            start = circleColor.start,
            stop = circleColor.end,
            fraction = it,
        )
    }

    val center = size * 0.5f

    val strokeWidth =
        outerCircleRadiusProgress * center.toPx() -
                (innerCircleRadiusProgress * center.toPx())

    if (strokeWidth > 0) {
        drawCircle(
            center = Offset(center.toPx(), center.toPx()),
            brush = SolidColor(color),
            radius = (outerCircleRadiusProgress * center.toPx()),
            style = Stroke(width = strokeWidth.dp.toPx())
        )
    }
}