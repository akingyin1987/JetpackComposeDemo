package com.example.jetpackcomposedemo.components




import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap

import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme


/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/30 14:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/30 14:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Composable
fun ArcProgress(modifier: Modifier,arcProgressData: ArcProgressData){
    val animateAngle: Float by animateFloatAsState(
        targetValue = 180F,
        animationSpec = tween(
            1000, 0,
            LinearEasing
        )
    )

    var currentProgress by remember {
        mutableStateOf(1F)
    }

    Text(text = "测试", color = Color.Black, modifier = Modifier.size(100.dp).drawWithContent {

        drawCircle(color = Color.Blue, style = Stroke(width = 5F) )
        drawContent()
    })
    Text(text = "测试2", color = Color.Black, modifier = Modifier.padding(18.dp).drawBehind {

        drawCircle(color = Color.Red, 18.dp.toPx()/2,center= Offset(drawContext.size.width,0f) )

    }.border(3.dp, color = Color.Black))
    Canvas(modifier = modifier ){
      //  drawArc()
      //  val rectF = Rect(arcProgressData.strokeWidth.value/2F,arcProgressData.strokeWidth.value/2F,size.width - arcProgressData.strokeWidth.value/2F,size.height-arcProgressData.strokeWidth.value/2F)
        val rectF = Rect(0F,0F,100F,100F)

        drawIntoCanvas { canvas ->
            //转为原生的canvas
             var startAngle  = 270 - arcProgressData.angle/2F
             var finishedStartAngle = startAngle
            if (currentProgress.toInt() == 0){
                finishedStartAngle = 0.01f;
            }

            val finishedSweepAngle: Float = currentProgress / arcProgressData.max* arcProgressData.angle
            var paint = Paint().apply {
                color  = arcProgressData.finishedColor
                isAntiAlias = true
                strokeWidth = arcProgressData.strokeWidth.value
                style = PaintingStyle.Stroke
                strokeCap = StrokeCap.Round
            }
            println("rectf=${rectF}")

             canvas.drawArc(rectF, finishedStartAngle,finishedSweepAngle,false,paint)
             
        }
      //  drawCircle(color = Color.Blue, style = Stroke(width = 5F) )
        drawArc(startAngle = 180F, sweepAngle = animateAngle, useCenter = false, color = Color.Red, style = Stroke(width = 5F, cap = StrokeCap.Round, miter = 1f))
        drawArc(startAngle = animateAngle, useCenter = false, sweepAngle = -180F,color = Color.Green, style = Stroke(width = 5F, cap = StrokeCap.Round, miter = 1f))
       
    }
}


@Preview
@Composable
fun ArcProgressPreview(){
    JetpackComposeDemoTheme {
        Surface(modifier = Modifier.fillMaxWidth()){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                ArcProgress(modifier = Modifier
                    .width(100.dp)
                    .height(100.dp), arcProgressData = ArcProgressData())
            }

        }
    }
}