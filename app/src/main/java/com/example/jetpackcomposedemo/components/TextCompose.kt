package com.example.jetpackcomposedemo.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    fun textComposeDemo(){
        Column(modifier = Modifier.border(1.dp,Color.Black)) {
            //列，即可看成纵向布局

            Row{
                //行， 即可看成横向布局
                Text("默认")

                Text(text = "斜体", fontStyle = FontStyle.Italic)

                Text(text = "粗体", fontWeight = FontWeight.Bold)

                Text(text = "居中", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

              //  Text( text = AnnotatedString(text = "大小、颜色"), color = Color.White , fontSize = 16.sp)

            }

            Row(modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(3.dp)){
                //行， 即可看成横向布局
                Text(text = "最大行数据".repeat(50), maxLines = 2)

            }
            Row(modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(3.dp)){
                //行， 即可看成横向布局
                Text(text = "文字溢出".repeat(50), maxLines = 2, overflow = TextOverflow.Ellipsis)

            }

            Row(modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(3.dp)) {
                Text(text = "字体1", fontFamily = FontFamily.Serif)
            }

            Row(modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(3.dp)) {
                Text(text = "字体2", fontFamily = FontFamily.SansSerif)
            }
            
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

               })
            }
        }



    }

    @Preview
    @Composable
    fun DefaultPreview(){
//        Column {
//            MarqueeText(text = "这是一段很长的数据",modifier =Modifier.padding(10.dp).background(color = Color.White))
//        }
        textComposeDemo()
    }


}