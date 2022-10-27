/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 * 隐式传递
 * @Description:
 * @author: aking
 * @CreateDate: 2022/10/25 16:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/25 16:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

data class Elevations(val card: Dp = 0.dp)

/** 自定义 CompositionLocalProvider  policy :数据改变策略 */
val LocalElevations = compositionLocalOf(policy = structuralEqualityPolicy()) {
    Elevations()
}

object CardElevations {
    val height: Elevations
        get() = Elevations(10.dp)

    val low: Elevations
        get() = Elevations(5.dp)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyCard(
    elevation: Dp = LocalElevations.current.card,
    backgroundColor: Color,
    context: @Composable () -> Unit
) {
   Card(onClick = { }, modifier = Modifier
       .padding(top = 10.dp)
       .fillMaxWidth()
       .height(100.dp), elevation = elevation, shape = RoundedCornerShape(elevation), backgroundColor = backgroundColor) {
         context.invoke()
   }
}

data class User(
    var name: String,
    var lastActiveTime: String,
    var time: Long = System.currentTimeMillis()
)


@Preview
@Composable
fun PhotographerCard() {
    JetpackComposeDemoTheme {

        val activeUser = compositionLocalOf {
            User("小明", "13分钟")
        }
        // 通过 current 方法取出当前值
        val user = activeUser.current
        Column(
            modifier = Modifier
                .fillMaxSize().background(MaterialTheme.colors.background)
        ) {

            Text(text = user.name + "1")
            CompositionLocalProvider(LocalContentColor provides LocalContentColor.current.copy(alpha = ContentAlpha.disabled)) {
                Text(text = user.name)
            }
            Text(text = user.name + "2")

            androidx.compose.material.Text(text = user.name)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                androidx.compose.material.Text(user.lastActiveTime)
            }

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                androidx.compose.material.Text(user.lastActiveTime)
            }

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                androidx.compose.material.Text(user.lastActiveTime)
            }

            // 通过 providers 中缀表达式可以重新对 CompositionLocal 实例赋值
            CompositionLocalProvider(activeUser provides User("小线", "5分钟")) {
                val newUser = activeUser.current
                Text(user.name, fontWeight = FontWeight.Bold)
                Text(newUser.name, fontWeight = FontWeight.Bold)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(newUser.lastActiveTime, style = MaterialTheme.typography.body2)
                }


            }
            MyCard(backgroundColor = Color.Red) {

            }
            CompositionLocalProvider(LocalElevations provides CardElevations.height) {
                MyCard(backgroundColor = Color.Red) {

                }
            }
            CompositionLocalProvider(LocalElevations provides CardElevations.low) {
                MyCard(backgroundColor = Color.Red) {

                }
            }
        }
    }

}
