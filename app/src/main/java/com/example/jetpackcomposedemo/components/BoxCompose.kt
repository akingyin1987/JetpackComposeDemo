package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Box 可将一个元素放在另一个元素上,与 xml 布局的 FrameLayout 类似
 * @Description:
 * @author: aking
 * @CreateDate: 2022/7/27 11:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/7/27 11:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class BoxCompose {


    @Composable
    fun BoxComposeDemo(){
        var bgColor by remember { mutableStateOf(Color.Red) }
        Column(modifier = Modifier.border(border = BorderStroke(1.dp,Color.Black))) {
            Box {
                Box(modifier = Modifier.size(100.dp,100.dp).background(Color.Black).clickable {
                    bgColor = Color.Green
                }){

                }
                Box(modifier = Modifier.size(50.dp,50.dp).background(bgColor).clickable {
                    bgColor = Color.DarkGray
                }){

                }
            }
        }
    }

    @Preview
    @Composable
    fun DefaultPreview(){

        BoxComposeDemo()
    }
}