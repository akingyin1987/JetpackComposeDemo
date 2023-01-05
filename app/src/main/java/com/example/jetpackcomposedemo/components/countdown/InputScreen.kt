/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.example.jetpackcomposedemo.components.countdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.ui.theme.Blue8A
import com.example.jetpackcomposedemo.ui.theme.Gray9A
import com.example.jetpackcomposedemo.ui.theme.bonava
import com.example.jetpackcomposedemo.ui.theme.product
import com.example.jetpackcomposedemo.viewmodel.CountdownTimerViewModel

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/4 14:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/4 14:03
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Composable
fun InputScreen(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopTimeDisplay()
            Divider(
                color = Gray9A,
                modifier = Modifier
                    .padding(top = 44.dp, bottom = 20.dp)
            )
            NumberKeyBoard()
        }
    }
}



@Composable
fun NumberKeyBoard() {
    val viewModel: CountdownTimerViewModel = viewModel()

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {

        items(viewModel.numbers) { number ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = if (number.isEmpty()) null else rememberRipple(color = Gray9A),
                        onClick = {
                            if (number.isNotEmpty()) {
                                viewModel.inputNumber(number)
                            }
                        }
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontFamily = product,
                    text = number,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp
                )
            }
        }
    }
}

@Composable
private fun TopTimeDisplay() {
    val viewModel: CountdownTimerViewModel = viewModel()
    val fullNumbers = viewModel.fullNumbers
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            buildAnnotatedString {
                val colorTime = if (viewModel.inputNumbers.isNotEmpty()) Blue8A else Gray9A
                withStyle(style = SpanStyle(color = colorTime, fontSize = 52.sp)) {
                    append(fullNumbers.substring(0, 2))
                }
                withStyle(style = SpanStyle(color = colorTime, fontSize = 18.sp)) {
                    append("h    ")
                }
                withStyle(style = SpanStyle(color = colorTime, fontSize = 52.sp)) {
                    append(fullNumbers.substring(2, 4))
                }
                withStyle(style = SpanStyle(color = colorTime, fontSize = 18.sp)) {
                    append("m    ")
                }
                withStyle(style = SpanStyle(color = colorTime, fontSize = 52.sp)) {
                    append(fullNumbers.substring(4, 6))
                }
                withStyle(style = SpanStyle(color = colorTime, fontSize = 18.sp)) {
                    append("s   ")
                }
            },
            fontFamily = bonava, textAlign = TextAlign.Center
        )
        Icon(
            painterResource(R.drawable.ic_remove),
            "remove",
            tint = Gray9A,
            modifier = Modifier.clickable {
                viewModel.removeNumber()
            }
        )
    }
}


@Preview
@Composable
fun InputScreenPreview(){
    InputScreen()
}