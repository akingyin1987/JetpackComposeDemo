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
 * @CreateDate: 2023/1/8 13:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/8 13:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.jetpackcomposedemo.components.likeButton.painter.drawBubble
import com.example.jetpackcomposedemo.components.likeButton.painter.drawLikeCircle
import kotlinx.coroutines.launch


@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    likeButtonState: LikeButtonState = rememberLikeButtonState(),
    size: Dp = 30.dp,
    likeContent: @Composable (isLiked: Boolean) -> Unit = {
        DefaultContent(
            isLike = it,
            size = size,
        )
    },
    circleSize: Dp = size * 0.8,
    bubblesCount: Int = 7,
    bubbleColor: BubbleColor = BubbleColor(
        dotPrimaryColor = Color(0xFFFFC107),
        dotSecondaryColor = Color(0xFFFF9800),
        dotThirdColor = Color(0xFFFF5722),
        dotLastColor = Color(0xFFF44336),
    ),
    onTap: (suspend () -> Unit)? = null,
) {

    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(size)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onTap?.let {
                    coroutineScope.launch {
                        it.invoke()
                    }
                } ?: coroutineScope.launch {
                    if (likeButtonState.isLiked.value) {
                        likeButtonState.unlike()
                    } else {
                        likeButtonState.like(coroutineScope)
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .size(
                    likeButtonState.scaleAnimation.value * size
                )
                .padding(
                    start = (size - likeButtonState.scaleAnimation.value * size) / 2,
                    top = (size - likeButtonState.scaleAnimation.value * size) / 2,
                )
        ) {
            likeContent(likeButtonState.isLiked.value)
        }

        Box(
            modifier = Modifier
                .size(circleSize)
                .padding(
                    start = (size - circleSize) / 2,
                    top = (size - circleSize) / 2,
                )
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawLikeCircle(
                    innerCircleRadiusProgress = likeButtonState.innerCircleAnimation.value,
                    outerCircleRadiusProgress = likeButtonState.outerCircleAnimation.value,
                    size = circleSize,
                )
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(-size.toPx() / 2, -size.toPx() / 2) {
                drawBubble(
                    size = Size(size.toPx() * 2, size.toPx() * 2),
                    currentProgress = likeButtonState.bubblesAnimation.value,
                    bubblesCount = bubblesCount,
                    bubbleColor = bubbleColor,
                )
            }
        }
    }
}


@Preview
@Composable
fun LinkButtonPreview(){
    LikeButton(likeContent = {
        Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null, tint = if(it) Color.Red else Color.Gray)
    })
}