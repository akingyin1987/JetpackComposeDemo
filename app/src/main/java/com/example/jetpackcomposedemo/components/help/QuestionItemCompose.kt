/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.help

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.jetpackcomposedemo.components.likeButton.LikeButton
import com.example.jetpackcomposedemo.components.likeButton.rememberLikeButtonState
import kotlinx.coroutines.launch

/**
 *  问题显示
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/9 11:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/9 11:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Composable
fun QuestionItemView(
    title: String,
    content: AnnotatedString,
    vararg tags: String,
    onClick: (annotation: AnnotatedString.Range<String>) -> Unit = {}
) {
    var checkState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val likeState = rememberLikeButtonState()
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .testTag("问题标题")
//                .constrainAs(topTitle) {
//                    top.linkTo(parent.top, 5.dp)
//                    start.linkTo(parent.start)
//                    bottom.linkTo(centerContent.top)
//                }
        )


        ConstraintLayout(modifier = Modifier.padding(top = 25.dp, bottom = 40.dp).fillMaxWidth()) {
            val (centerContent,btnBottom)=createRefs()

            ClickableText(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState)
                    .constrainAs(centerContent) {
                        top.linkTo(parent.top)
                        bottom.linkTo(btnBottom.top)
                        // height = Dimension.fillToConstraints

                    },
                style = MaterialTheme.typography.bodyMedium,
                text = content,
                onClick = { offset ->
                    tags.forEach { tag ->
                        content.getStringAnnotations(tag, start = offset, end = offset).firstOrNull()
                            ?.let { annotation ->
                                onClick.invoke(annotation)
                            }
                    }
                })

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .constrainAs(btnBottom) {
                    top.linkTo(centerContent.bottom, 5.dp)
                    bottom.linkTo(parent.bottom)
                }, horizontalArrangement = Arrangement.SpaceBetween) {

                LikeButton(modifier = Modifier
                    .weight(1F)
                    .wrapContentHeight()
                    .padding(start = 5.dp), likeButtonState = likeState, likeContent = {
                    OutlinedButton(onClick = {
                        checkState = false
                        scope.launch {
                            if (likeState.isLiked.value) {
                                likeState.unlike()
                            } else {

                                likeState.like(scope)
                            }

                        }
                    },shape = RoundedCornerShape(4.dp)) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = null,
                            tint = if (it) Color.Red else Color.Gray
                        )
                        Text(text = "问题已解决", color = if (it) Color.Red else LocalContentColor.current,modifier = Modifier.padding(start = 5.dp))
                    }

                }, onTap = {
                    scope.launch {
                        if (likeState.isLiked.value) {
                            likeState.unlike()
                        } else {
                            checkState = false
                            likeState.like(scope)
                        }
                    }
                })

                Spacer(modifier = Modifier.weight(0.5F))
                OutlinedButton(onClick = {
                    scope.launch {
                        likeState.unlike()
                    }
                    checkState = checkState.not()
                }, shape = RoundedCornerShape(4.dp),modifier= Modifier
                    .weight(1F)
                    .padding(end = 5.dp)) {
                    Icon(
                        imageVector = Icons.Default.ThumbDown,
                        contentDescription = null,
                        tint = if (checkState) Color.Red else Color.Gray
                    )
                    Text(
                        text = "问题未解决",
                        color = if (checkState) Color.Red else LocalContentColor.current,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }

            }


        }
   }


}


@Preview
@Composable
fun QuestionItemPreview() {
    QuestionItemView(title = "这是个问题".repeat(4),
        content = buildAnnotatedString {
            append("Click 我要重重复很多数据".repeat(250))

            // We attach this *URL* annotation to the following content
            // until `pop()` is called
            pushStringAnnotation(tag = "URL",
                annotation = "https://developer.android.com")
            withStyle(style = SpanStyle(color = Color.Blue,
                fontWeight = FontWeight.Bold)
            ) {
                append("here")
            }

            pop()
        },"URL", onClick = {
            println("it=${it.item},${it.tag}")
        })
}


