/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.banner

import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.util.lerp

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/2/3 18:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/2/3 18:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
fun GraphicsLayerScope.scaleInGraphics(offset: Float, start: Float = 0.85f, stop: Float = 1f) {
    lerp(
        start = start,
        stop = stop,
        fraction = 1f - offset.coerceIn(0f, 1f)
    ).also { scale ->
        scaleX = scale
        scaleY = scale
    }
}


fun GraphicsLayerScope.alphaInGraphics(offset: Float, start: Float = 0.5f, stop: Float = 1f) {
    alpha = lerp(
        start = start,
        stop = stop,
        fraction = 1f - offset.coerceIn(0f, 1f)
    )
}