package com.example.jetpackcomposedemo.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/30 12:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/30 12:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Composable
fun CircularViewCompose(){
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator()
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

            CircularProgressIndicator(progress = 0.5F, strokeWidth = 5.dp, color = MaterialTheme.colorScheme.primary)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

            LinearProgressIndicator(
                //设置水平进度条当前进度颜色
                color = Color.Green,
                //设置水平进度条总长度颜色
               // backgroundColor = Color.Yellow,
                //设置水平进度条当前进度
                  progress = 1F,
                modifier = Modifier.fillMaxWidth()
            )

        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

            androidx.compose.material.LinearProgressIndicator(
                //设置水平进度条当前进度颜色
                color = Color.Green,
                //设置水平进度条总长度颜色
                 backgroundColor = Color.Black,
                //设置水平进度条当前进度
                progress = 0.5F,
                modifier = Modifier.fillMaxWidth()
            )

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

            androidx.compose.material.CircularProgressIndicator(progress = 0.5F, strokeWidth = 5.dp, color = MaterialTheme.colorScheme.primary)
        }

    }

}

const val TAG ="CircleProgress"

@Composable
fun CircleProgress(){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        //The distance between the size of each point
        val pointsSizeDistance = 3f
        //the points radius
        val pointsRadius = 15f

        val baseCircleHeightRadius = 200f

        val allPointsRadius =
            (pointsRadius - (pointsSizeDistance * 0)) + (pointsRadius - (pointsSizeDistance * 1)) + (pointsRadius - (pointsSizeDistance * 2)) + (pointsRadius - (pointsSizeDistance * 3))
        Log.d(TAG, "CircleProgress: alll = $allPointsRadius")
        val pointsDistance = (baseCircleHeightRadius - (allPointsRadius * 2)) / 2f
        Log.d(TAG, "CircleProgress: dis = $pointsDistance")
        ////////////////////////////////////////////////////


        val radius1 = baseCircleHeightRadius - (pointsDistance * 0)
        val radius2 = baseCircleHeightRadius - (pointsDistance * 1)
        val radius3 = baseCircleHeightRadius - (pointsDistance * 2)
        val radius4 = baseCircleHeightRadius - (pointsDistance * 3)

        Log.d(TAG, "CircleProgress 1: $radius1")
        Log.d(TAG, "CircleProgress 2: $radius2")
        Log.d(TAG, "CircleProgress 3: $radius3")
        Log.d(TAG, "CircleProgress 4: $radius4")


        val animateFloat1 = remember { Animatable(0f) }
        LaunchedEffect(animateFloat1) {
            animateFloat1.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        delayMillis = 300,
                        durationMillis = 2000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        val animateFloat2 = remember { Animatable(0f) }
        LaunchedEffect(animateFloat2) {
            animateFloat2.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        delayMillis = 300,
                        durationMillis = 2000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        val animateFloat3 = remember { Animatable(0f) }
        LaunchedEffect(animateFloat3) {
            animateFloat3.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        delayMillis = 300,
                        durationMillis = 2000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
        val animateFloat4 = remember { Animatable(0f) }
        LaunchedEffect(animateFloat4) {
            animateFloat4.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        delayMillis = 300,
                        durationMillis =2000,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {


            // this has changed
            val startInDisplay = size.height * 0.156f

            drawCircle(
                color = Color.Black,
                radius = pointsRadius - (pointsSizeDistance * 0),
                center = Offset(size.width * 0.5f, startInDisplay + (pointsDistance * 0))
            )
            drawCircle(
                color = Color.Black,
                radius = pointsRadius - (pointsSizeDistance * 1),
                center = Offset(size.width * 0.5f, startInDisplay + (pointsDistance * 1))
            )
            drawCircle(
                color = Color.Black,
                pointsRadius - (pointsSizeDistance * 2),
                center = Offset(size.width * 0.5f, startInDisplay + (pointsDistance * 2))
            )
            drawCircle(
                color = Color.Black,
                pointsRadius - (pointsSizeDistance * 3),
                center = Offset(size.width * 0.5f, startInDisplay + (pointsDistance * 3))
            )

            ///////////////////////////////////////////////////////////////////////////

            //case 1
            drawArc(
                color = Color.Black,
                startAngle = 270f,
                sweepAngle = 360f * animateFloat1.value,
                useCenter = false,
                topLeft = Offset(
                    (size.width * 0.5f) - radius1,
                    startInDisplay + (pointsDistance * 0)
                ),
                size = Size(
                    radius1 * 2,
                    radius1 * 2
                ),
                style = Stroke((pointsRadius - (pointsSizeDistance * 0)) * 2, cap = StrokeCap.Round)
            )

            //case 2
            drawArc(
                color = Color.Black,
                startAngle = 270f,
                sweepAngle = 360f * animateFloat2.value,
                useCenter = false,
                topLeft = Offset(
                    (size.width * 0.5f) - radius2,
                    startInDisplay + (pointsDistance * 1)
                ),
                size = Size(
                    radius2 * 2,
                    radius2 * 2
                ),
                style = Stroke((pointsRadius - (pointsSizeDistance * 1)) * 2, cap = StrokeCap.Round)
            )

            //case 3
            drawArc(
                color = Color.Black,
                startAngle = 270f,
                sweepAngle = 360f * animateFloat3.value,
                useCenter = false,
                topLeft = Offset(
                    (size.width * 0.5f) - radius3,
                    startInDisplay + (pointsDistance * 2)
                ),
                size = Size(
                    radius3 * 2,
                    radius3 * 2
                ),
                style = Stroke((pointsRadius - (pointsSizeDistance * 2)) * 2, cap = StrokeCap.Round)
            )

            //case 4
            drawArc(
                color = Color.Black,
                startAngle = 270f,
                sweepAngle = 360f * animateFloat4.value,
                useCenter = false,
                topLeft = Offset(
                    (size.width * 0.5f) - radius4,
                    startInDisplay + (pointsDistance * 3)
                ),
                size = Size(
                    radius4 * 2,
                    radius4 * 2
                ),
                style = Stroke((pointsRadius - (pointsSizeDistance * 3)) * 2, cap = StrokeCap.Round)
            )

        }


    }
}


@Composable
 fun CircleProgress2(
    angle: Float,
    progressColor: List<Color> = listOf( Color.DarkGray,Color.Gray,Color.LightGray),
    backgroundProgressColor: List<Color> = listOf(Color.Transparent,Color.Transparent),
    centerColor : Color = Color.White,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {

                val sizeMultiplier = 0.70f
                val strokeWidth = size.width / 60

                val drawSize = Size(
                    size.width * sizeMultiplier,
                    size.height * sizeMultiplier
                )

                val drawOffset = Offset(
                    size.width / 2 - (drawSize.width / 2),
                    size.height / 2 - (drawSize.height / 2)
                )

                drawArc(
                    brush = Brush.verticalGradient(
                        backgroundProgressColor
                    ),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    topLeft = drawOffset,
                    size = drawSize,
                    useCenter = false,
                    style = Stroke(width = strokeWidth)
                )
                drawArc(
                    brush = Brush.verticalGradient(
                        progressColor
                    ),
                    topLeft = drawOffset,
                    size = drawSize,
                    startAngle = -90f,
                    sweepAngle = angle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                drawArc(
                    color = centerColor,
                    topLeft = drawOffset + Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(drawSize.width - strokeWidth, drawSize.height - strokeWidth),
                    startAngle = -90f,
                    sweepAngle = 360.0f,
                    useCenter = true
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}


@Composable
fun CircleProgress3(sweepAngle: Float, finishedListener: () -> Unit = {}) {
    val animateAngle: Float by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(
            1000, 0,
            LinearEasing
        ),
        finishedListener = {
            finishedListener.invoke()
        }
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = 80.dp.toPx()
        val arcSize = radius * 2

        drawCircle(
            color = Color.Green,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            style = Stroke(width = 5F),
            radius = radius
        )

        drawArc(
            color = Color.Black,
            startAngle = -90f,
            sweepAngle = animateAngle,
            useCenter = false,
            topLeft = Offset(x = (canvasWidth / 2) - radius, y = (canvasHeight / 2 - radius)),
            size = Size(width = arcSize, height = arcSize),
            style = Stroke(width = 5F, cap = StrokeCap.Round, miter = 1f)
        )
    }
}

@Preview
@Composable
fun ProgressPreview() {

    JetpackComposeDemoTheme {
        Surface{
            CircleProgress3(sweepAngle = 80F)
        }
    }

}
