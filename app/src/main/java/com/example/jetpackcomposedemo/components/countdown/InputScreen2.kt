/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.countdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/4 15:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/4 15:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@Composable
fun InputScreen2(onStart: (it: Int) -> Unit) {
    var input by remember {
        mutableStateOf(listOf<Int>())
    }

    val (hou, min, sec) = remember(input) {
        mutableListOf<Int>().run {
            repeat(6 - input.size) {
                add(0)
            }
            addAll(input)

            Triple(
                "${get(0)}${get(1)}",
                "${get(2)}${get(3)}",
                "${get(4)}${get(5)}"
            )
        }
    }
    val hasCountdownValue = remember(input) {
        input.isNotEmpty()
    }

    Column(modifier = Modifier.fillMaxHeight()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 30.dp, end = 30.dp)
        ) {
            listOf(hou to "h", min to "m", sec to "s").forEach {
               DisplayTime(it.first,it.second,it.first != "00")
            }
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(enabled = hasCountdownValue) {
                        input = input
                            .toMutableList()
                            .apply {
                                if (isNotEmpty()) {
                                    removeLast()
                                }
                            }
                    },
                imageVector = Icons.Default.Backspace,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.Unspecified.copy(
                        if (hasCountdownValue) 1.0f else 0.5f
                    )
                )
            )
        }
        Divider(
            modifier = Modifier.padding(20.dp),
            color = Color.LightGray, thickness = 0.5.dp
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)) {
            val onClick = remember {
                {it:Int->
                    if(input.size<6){
                        input = input.toMutableList() + it
                    }
                }
            }

            listOf((1..3),(4..6),(7..9)).forEach {
                Row(modifier = Modifier.padding(10.dp)) {
                    it.forEach {
                        InputButton(num = it ){ result->
                            onClick(result)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                InputButton(0) {
                    if (hasCountdownValue) {
                        onClick(0)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(30.dp))
        if(hasCountdownValue){
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(70.dp)
                    .shadow(30.dp, shape = CircleShape)
                    .clickable {
                      onStart(
                          hou.toInt() * 60 * 60 +
                                  min.toInt() * 60 +
                                  sec.toInt()

                      )
                    },
                imageVector = Icons.Default.PlayCircle,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
}


@Composable
fun RowScope.DisplayTime(
    num: String,
    label: String = "",
    heightLight: Boolean = false,
    fontSize: TextUnit = displayFontSize,
    labelSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.End
) {
    val textColor =
        if (heightLight) MaterialTheme.colorScheme.primary else Color.Unspecified.copy(alpha = 0.5F)
    Text(
        text = num,
        modifier = Modifier
            .weight(1F)
            .align(alignment = Alignment.CenterVertically),
        textAlign = textAlign,
        fontSize = fontSize,
        fontFamily = FontFamily.Cursive,
        color = textColor,
        style = MaterialTheme.typography.titleMedium
    )

    if(label.isNotEmpty()){
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterVertically),
            text = label,
            fontSize = labelSize,
            color = textColor
        )
        Spacer(modifier = Modifier.width(15.dp))
    }
}

@Composable
fun RowScope.InputButton(num: Int, onClick: (Int) -> Unit) {
    Text(
        num.toString(),
        Modifier
            .weight(1f)
            .clickable { onClick(num) },
        fontSize = inputFontSize,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Preview
@Composable
fun Input2Preview() {
    JetpackComposeDemoTheme(darkTheme = false) {
        InputScreen2 {}
    }
}


val inputFontSize = 35.sp
val displayFontSize = 50.sp