package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

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

    /**
     * padding 在元素周围留出空间
     */
    @Composable
    fun TextComposeDemo() {
        var text by remember { mutableStateOf("") }

        var clickCount by remember {
            mutableStateOf(0)
        }
        var count = 0


        Column(modifier = Modifier.border(1.dp, Color.Black)) {
            //列，即可看成纵向布局

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(3.dp)), contentAlignment = Alignment.Center){
                Text(text = "居中1", textAlign = TextAlign.Center)
            }
            


            Column(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text(text = "第1项")
                Text(text = "第2项")
                Text(text = "第3项")
            }

            
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

            //padding 放在背景色的后面
            Row( modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(3.dp)
                .fillMaxWidth()) {
                Text(text = "padding", modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Green))
            }
            //padding 放在背景色的前面 相当于margin
            Row( modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(3.dp)
                .fillMaxWidth()) {
                Text(text = "padding=margin", modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Green)
                    .padding(10.dp))
            }
            //padding放在了设置宽高之前，所以呈现出来的就有了上面的类似于android xml中的margin效果，这里可以这样来理解：
            Row( modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(2.dp)
                .fillMaxWidth()) {
                Text(text = "padding设置在宽高之前", modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.Black))
            }
        }


    }

    @Preview
    @Composable
    fun TextComposePreview() {

//        Column {
//            MarqueeText(text = "这是一段很长的数据",modifier =Modifier.padding(10.dp).background(color = Color.White))
//        }
        //TextComposeDemo()

        JetpackComposeDemoTheme {
            val scrollState = rememberScrollState()
            ConstraintLayout(modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxWidth()
                .wrapContentHeight()) {
                val (centerContent,btnBottom)=createRefs()
                ClickableText(text = buildAnnotatedString {
                     append("这是很长很长的文本".repeat(200))
                } , onClick ={} , modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState)
                    .constrainAs(centerContent) {
                        top.linkTo(parent.top)
                        bottom.linkTo(btnBottom.top, 5.dp)
                    })
//                Text(text = "这是很长很长的文本".repeat(200),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .verticalScroll(state = scrollState)
//                        .constrainAs(centerContent) {
//                            top.linkTo(parent.top)
//                            bottom.linkTo(btnBottom.top, 5.dp)
//                        }
//                )
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(btnBottom) {
                            top.linkTo(centerContent.bottom, 5.dp)
                            bottom.linkTo(parent.bottom, 10.dp)
                        }) {
                    Text(text = "我是文本下面的按钮")
                }
            }
        }

//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .verticalScroll(state = scrollState),
//            verticalArrangement = Arrangement.Center) {
//            Text(text = "这是很长很长的文本".repeat(200),
//                modifier = Modifier
//                    .fillMaxWidth()
//                 //   .verticalScroll(state = rememberScrollState())
//            )
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "我是文本下面的按钮")
//            }
//        }
    }


