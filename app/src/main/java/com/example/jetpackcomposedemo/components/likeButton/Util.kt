/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.likeButton
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/8 13:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/8 13:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@Composable
fun DefaultContent(
    isLike: Boolean = false,
    modifier: Modifier,
) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.Favorite,
        contentDescription = "",
        tint = if (isLike) Color.Red else Color.Gray,
    )
}

fun degToRad(deg: Float) = deg * (PI / 180.0)

fun mapValueFromRangeToRange(
    value: Float,
    fromLow: Float,
    fromHigh: Float,
    toLow: Float,
    toHigh: Float,
): Float {
    return toLow + (value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow)
}

fun Int.forEach(
    block: (Int) -> Unit,
) {
    for (i in 0 until this) {
        block.invoke(i)
    }
}

operator fun Dp.times(d: Double) = this.value.times(d).dp