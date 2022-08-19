package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 文本类的组件
 * @Description:
 * @author: aking
 * @CreateDate: 2022/7/25 11:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/7/25 11:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class TextCompose {

    @Composable
    fun textComposeDemo() {
        var text by remember { mutableStateOf("") }

        var clickCount by remember {
            mutableStateOf(0)
        }
        var count = 0


        Column(modifier = Modifier.border(1.dp, Color.Black)) {
            //列，即可看成纵向布局

            Row {
                //行， 即可看成横向布局
                Text("默认")
                Text("text=${text}")
                Text(text = "斜体", fontStyle = FontStyle.Italic)

                Text(text = "粗体", fontWeight = FontWeight.Bold)

                Text(text = "居中", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

                //  Text( text = AnnotatedString(text = "大小、颜色"), color = Color.White , fontSize = 16.sp)

            }

            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(3.dp)
            ) {
                //行， 即可看成横向布局
                Text(text = "最大行数据".repeat(50), maxLines = 2)

            }
            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(3.dp)
            ) {
                //行， 即可看成横向布局
                Text(text = "文字溢出".repeat(50), maxLines = 2, overflow = TextOverflow.Ellipsis)

            }

            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(3.dp)
            ) {
                Text(text = "字体1", fontFamily = FontFamily.Serif)
            }

            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(3.dp)
            ) {
                Text(text = "字体2", fontFamily = FontFamily.SansSerif)
            }
            println("test-->")

            Row {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("H")
                    }
                    append("ello ")

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                        append("W")

                    }
                    append("orld")

                }, modifier = Modifier.clickable {
                    println("点击TextView")
                    clickCount++
                    count++
                    println("count=${count}")
                    text = "$count hello word${clickCount}"

                })
            }

            Row(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(3.dp)
            ) {
                //行， 即可看成横向布局
                Text(text = buildAnnotatedString {
                    append("我已阅读并同意")
                    withStyle(
                        style = SpanStyle(
                            //设置这部分的文字颜色
                            color = Color.Blue,
                            //设置文本为斜体
                            fontStyle = FontStyle.Italic,
                            //添加下划线
                            textDecoration = TextDecoration.Underline,
                            shadow = Shadow(
                                // shadow 为文字添加阴影效果
                                color = Color.Red,
                                //设置阴影的偏移量
                                offset = Offset(4F, 4F),
                                //设置阴影半径
                                blurRadius = 2F
                            )
                        )
                    ){
                        append("用户协议")
                    }
                    append("和")
                    withStyle(style = SpanStyle(
                        color = Color.Blue,//color设置这部分文字的颜色
                        fontStyle = FontStyle.Italic,//设置文本为斜体
                        textDecoration = TextDecoration.Underline,//添加下划线

                    )){
                        append("隐私政策")
                    }
                })

            }
        }


    }

    @Preview
    @Composable
    fun DefaultPreview() {

//        Column {
//            MarqueeText(text = "这是一段很长的数据",modifier =Modifier.padding(10.dp).background(color = Color.White))
//        }
        textComposeDemo()
    }


}