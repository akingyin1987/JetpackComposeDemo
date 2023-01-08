/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/8 12:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/8 12:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Preview
@Composable
fun DoubleTapToLike() {
    var transitionState by remember {
        mutableStateOf(MutableTransitionState(LikedStates.Disappeared))
    }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        transitionState = MutableTransitionState(LikedStates.Initial)
                    }
                )
            }
    ) {
        if (transitionState.currentState == LikedStates.Initial) {
            transitionState.targetState = LikedStates.Liked
        } else if (transitionState.currentState == LikedStates.Liked) {
            transitionState.targetState = LikedStates.Disappeared
        }

        val transition = updateTransition(transitionState = transitionState, label = null)
        val alpha by transition.animateFloat(
            transitionSpec = {
                when {
                    LikedStates.Initial isTransitioningTo LikedStates.Liked ->
                        keyframes {
                            durationMillis = 500
                            0f at 0
                            0.5f at 100
                            1f at 225
                        }
                    LikedStates.Liked isTransitioningTo LikedStates.Disappeared ->
                        tween(durationMillis = 200)
                    else -> snap()
                }
            }, label = ""
        ) {
            if (it == LikedStates.Liked) 1f else 0f
        }

        val scale by transition.animateFloat(
            transitionSpec = {
                when {
                    LikedStates.Initial isTransitioningTo LikedStates.Liked ->
                        spring(dampingRatio = Spring.DampingRatioHighBouncy)
                    LikedStates.Liked isTransitioningTo LikedStates.Disappeared ->
                        tween(200)
                    else -> snap()
                }
            }, label = ""
        ) {
            when (it) {
                LikedStates.Initial -> 0f
                LikedStates.Liked -> 4f
                LikedStates.Disappeared -> 2f
            }
        }

        Icon(
            Icons.Filled.Favorite,
            "点赞",
            Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    alpha = alpha,
                    scaleX = scale,
                    scaleY = scale
                ),
            tint = Color.Red
        )
    }
}
enum class LikedStates {
    Initial,
    Liked,
    Disappeared
}