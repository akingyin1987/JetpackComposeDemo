/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.animation

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/7/24 12:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/7/24 12:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@Preview
@Composable
fun TweenAnimationCompose(){
    val isRound by remember { mutableStateOf(false) }
    val easingState by remember {
        mutableStateOf(0)
    }
    val borderRadius by animateIntAsState(
        targetValue = if (isRound) 100 else 0,
        animationSpec = tween(
            //tween  使用缓动曲线的起始点到终点的动画规格
            durationMillis = 3_000,
            easing = when(easingState){
                0-> FastOutSlowInEasing
                1-> LinearOutSlowInEasing
                2->FastOutLinearInEasing
                3->LinearEasing
                else->{
                    CubicBezierEasing(1F,1F,1F,1F)
                }
            }
        )
    )


}