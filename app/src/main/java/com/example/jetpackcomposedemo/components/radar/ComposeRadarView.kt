/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.radar

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import java.util.regex.Pattern
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


/**
 * 雷达图
 * https://www.6hu.cc/archives/29257.html
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/5 10:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/5 10:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Composable
fun ComposeRadarView(
    modifier: Modifier = Modifier,
    data: List<RadarBean>,
    specialHandle: Boolean = false
) {
    val CIRCLE_TURN = 3
    val colors = arrayOf(Color(0xffeff3fe), Color(0xffe8eefd), Color(0xffdee6fc), Color(0xffd8e0fa), Color(0xffc3d0f7), Color(0x5989A3F0), Color(0xff3a65e6))
    var enable by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(if (enable) 1f else 0f, animationSpec = tween(2000))

    /** 有些时候我们绘制⼀些⽐较复杂的UI效果时，
     * 不希望当 Recompose 发⽣时所有绘画所⽤的所有实例都重新构建⼀次 (类似Path)，这可能会产⽣内存抖动。
     * 在Compose 中我们⼀般 能够想到使⽤ remember 进⾏缓存 ，
     * 然⽽我们所绘制的作⽤域是 DrawScope 并不是 Composable，所以⽆法使⽤ remember，那我们该怎么办呢？
      drawWithCache 提供了这 个能⼒
    */
    Canvas(modifier = modifier
        .border(1.dp, color = colors[6])
        .drawWithCache {
            val center = Offset(size.width / 2, size.height / 2)

            /** 最小的圆的半径 */
            val textNeedRadius = 25.dp.toPx()
            val radarRadius = center.x - textNeedRadius
            val turnRadius = radarRadius / CIRCLE_TURN

            /** 根据数据类型分块 */
            val itemAngle = 360 / data.size

            /** 开始绘制的角度（虚线 当数据为奇数时 第一条竖起向上 仅-90  为偶数时则对称 ）  */
            val startAngle = if (data.size % 2 == 0) {
                -90 - itemAngle / 2
            } else {
                -90
            }

            /** 画笔 */
            val textPaint = TextPaint().apply {
                isAntiAlias = true
                textSize = 10.sp.toPx()
                color = colors[6].toArgb()
            }

            /** 路径 */
            val path = Path()

            /** 返回绘制结果 */
            onDrawWithContent {
                path.reset()
                // 绘制三个圆形
                for (turn in 0 until CIRCLE_TURN) {
                    drawCircle(colors[turn], radius = turnRadius * (CIRCLE_TURN - turn))
                    drawCircle(
                        colors[3],
                        radius = turnRadius * (CIRCLE_TURN - turn),
                        style = Stroke(2f)
                    )
                }

                for (index in data.indices) {
                    // 绘制虚线
                    val currentAngle = startAngle + itemAngle * index
                    val xy = inCircleOffset(center, progress * radarRadius, currentAngle)
                    drawLine(
                        colors[4],
                        center,
                        xy,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    )

                    // 绘制文字 半径位置
                    val textPointRadius = progress * radarRadius + 10f
                    val offset = inCircleOffset(center, textPointRadius, currentAngle)
                    val text = data[index].text
                    wrapText(
                        text,
                        textPaint,
                        size.width,
                        offset,
                        currentAngle,
                        if (specialHandle) textPaint.textSize * 2 else null
                    )

                    // 绘制连接范围
                    val pointData = data[index]
                    val pointRadius = progress * radarRadius * pointData.value / 100
                    val fixPoint = inCircleOffset(center, pointRadius, currentAngle)
                    if (index == 0) {
                        path.moveTo(fixPoint.x, fixPoint.y)
                    } else {
                        path.lineTo(fixPoint.x, fixPoint.y)
                    }
                }
                path.close()
                drawPath(path, colors[5])
                drawPath(path, colors[6], style = Stroke(5f))
            }
        }
        .onGloballyPositioned {
            enable = it.boundsInRoot().top >= 0 && it.boundsInRoot().right > 0
        } ){

    }
}


/**
 * 根据圆心，半径以及角度获取圆形中的xy坐标
 * @receiver DrawScope
 * @param center Offset
 * @param radius Float
 * @param angle Int
 * @return Offset
 */
fun inCircleOffset(center: Offset, radius: Float, angle: Int): Offset {
    return Offset((center.x + radius * cos(angle * PI / 180)).toFloat(), (center.y + radius * sin(angle * PI / 180)).toFloat())
}


/**
 * 绘制换行文字
 * @receiver DrawScope
 * @param text String 绘制文本
 * @param textPaint TextPaint 文字画笔
 * @param width Float 雷达控件的宽度
 * @param offset Offset  未来调整前文字绘制的XY坐标
 * @param currentAngle Int 当前文字绘制所在的角度
 * @param chineseWrapWidth Float? 处理中文 每两个字符换行
 */
fun DrawScope.wrapText(
    text: String,
    textPaint: TextPaint,
    width: Float,
    offset: Offset,
    currentAngle: Int,
    chineseWrapWidth: Float? = null // 用来处理UI需求中文每两个字符换行
) {
    println("文本内容=${text},当前角度=${currentAngle}")
    val quadrant = quadrant(currentAngle)
    /** 文本最大宽度 */
    var textMaxWidth = width
    when (quadrant) {
        0 -> {
            //垂直类则为 控件宽度1/2
            textMaxWidth = width / 2
        }
        -1, 1, 2 -> {
            //在右边
            textMaxWidth = size.width - offset.x
        }
        -2, 3, 4 -> {
            //左边
            textMaxWidth = offset.x
        }
    }
    // 需要特殊处理换行&&包含中文字符&&文本绘制一行的宽度>文本最大宽度
    if (chineseWrapWidth != null && isContainChinese(text) && textPaint.measureText(text) > textMaxWidth) {
        textMaxWidth = chineseWrapWidth
    }
    val staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        StaticLayout.Builder.obtain(text, 0, text.length, textPaint, textMaxWidth.toInt()).apply {
            this.setAlignment(Layout.Alignment.ALIGN_NORMAL)
        }.build()
    } else {
        StaticLayout(text, textPaint, textMaxWidth.toInt(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0f, false)
    }
    /** 控件的高度 */
    val textHeight = staticLayout.height
    /** 文本行数 */
    val lines = staticLayout.lineCount
    /** 是否为多行 */
    val isWrap = lines > 1
    /** 文本的真实宽度 （如果是多行，则直接取第一行的宽度。单行则直接获取文本的宽度） */
    val textTrueWidth = if (isWrap) staticLayout.getLineWidth(0) else textPaint.measureText(text)
    drawContext.canvas.nativeCanvas.save()
    //计算平移（不平移则默认位置为左上角原点）
    when (quadrant) {
        0 -> {
            // drawContext.canvas.nativeCanvas.translate(offset.x - textTrueWidth / 2, offset.y - textHeight)
            drawContext.canvas.nativeCanvas.translate(offset.x - textTrueWidth / 2, offset.y - textHeight)
        }
        -1 -> {
            drawContext.canvas.nativeCanvas.translate(offset.x, offset.y - textHeight / 2)
        }
        -2 -> {
            drawContext.canvas.nativeCanvas.translate(offset.x - textTrueWidth, offset.y - textHeight / 2)
        }
        1 -> {
            drawContext.canvas.nativeCanvas.translate(
                offset.x,
                if (!isWrap) offset.y - textHeight / 2 else offset.y - (textHeight - textHeight / lines / 2)
            )
        }
        2 -> {
            drawContext.canvas.nativeCanvas.translate(offset.x, if (!isWrap) offset.y - textHeight / 2 else offset.y - textHeight / lines / 2)
        }
        3 -> {
            drawContext.canvas.nativeCanvas.translate(
                offset.x - textTrueWidth,
                if (!isWrap) offset.y - textHeight / 2 else offset.y - textHeight / lines / 2
            )
        }
        4 -> {
            drawContext.canvas.nativeCanvas.translate(
                offset.x - textTrueWidth,
                if (!isWrap) offset.y - textHeight / 2 else offset.y - (textHeight - textHeight / lines / 2)
            )
        }
    }
    staticLayout.draw(drawContext.canvas.nativeCanvas)
    drawContext.canvas.nativeCanvas.restore()
}

private fun isContainChinese(str: String): Boolean {
    val p = Pattern.compile("[\u4e00-\u9fa5]")
    val m = p.matcher(str)
    return m.find()
}

private fun quadrant(angle: Int): Int {
    println("当前角度=${angle}")
    return if (angle == -90 || angle == 90) {
        0 // 垂直
    } else if (angle == 0) {
        -1 // 水平右边
    } else if (angle == 180) {
        -2 // 水平左边
    } else if (angle > -90 && angle < 0) {
        1
    } else if (angle in 1..89) {
        2
    } else if (angle in 91..179) {
        3
    } else {
        4
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeRadarPreview() {
    JetpackComposeDemoTheme(darkTheme = true) {
        ComposeRadarView(modifier = Modifier.size(200.dp), data = listOf(
            RadarBean("基本财务基本财", 53f),
            RadarBean("基本财2务财务", 90f),
            RadarBean("基", 90f),
            RadarBean("基本1财务财务", 100f),
            RadarBean("基本123财务", 83f),
            RadarBean("技术择时择时", 50f),
            RadarBean("景气行业行业", 83f)),specialHandle = false)
    }

}