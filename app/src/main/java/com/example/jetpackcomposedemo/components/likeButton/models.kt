/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.likeButton

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/8 13:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/8 13:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
import androidx.compose.animation.core.Easing
import androidx.compose.ui.graphics.Color

data class CircleColor(
    val start: Color,
    val end: Color,
)

data class BubbleColor(
    val dotPrimaryColor: Color,
    val dotSecondaryColor: Color,
    val dotThirdColor: Color = dotPrimaryColor,
    val dotLastColor: Color = dotSecondaryColor,
) {
    private var index = 0
    fun next(): Color {
        if (index > 3) {
            index = 0
        }
        return when (index) {
            0 -> dotPrimaryColor
            1 -> dotSecondaryColor
            2 -> dotThirdColor
            3 -> dotLastColor
            else -> {
                dotLastColor
            }
        }.apply {
            index ++
        }
    }
}

val EaseOutBounce: Easing = Easing { fraction ->
    val n1 = 7.5625f
    val d1 = 2.75f
    var newFraction = fraction
    return@Easing if (newFraction < 1f / d1) {
        n1 * newFraction * newFraction
    } else if (newFraction < 2f / d1) {
        newFraction -= 1.5f / d1
        n1 * newFraction * newFraction + 0.75f
    } else if (newFraction < 2.5f / d1) {
        newFraction -= 2.25f / d1
        n1 * newFraction * newFraction + 0.9375f
    } else {
        newFraction -= 2.625f / d1
        n1 * newFraction * newFraction + 0.984375f
    }
}

val Decelerate: Easing = Easing { fraction ->
    return@Easing (1.0 - (1.0 - fraction) * (1.0 - fraction)).toFloat()
}