/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components


import android.widget.Chronometer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme


/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/12/28 11:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/12/28 11:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


sealed class ChronometerState{
    object Idle:ChronometerState()
    object Start:ChronometerState()
    object Stop:ChronometerState()
}

@Composable
fun AndroidChronometer(modifier: Modifier,baseTime:Long=0L,chronometerState: ChronometerState=ChronometerState.Idle,chronometerOnTick: Chronometer.OnChronometerTickListener) {

    AndroidView({ context ->
        val chronometer = Chronometer(context)
        chronometer.onChronometerTickListener = chronometerOnTick
        return@AndroidView chronometer
    }, modifier = modifier, update = {
        when(chronometerState){
            ChronometerState.Idle->{
                
            }
            ChronometerState.Start->{
                if(baseTime>0){
                    it.base = baseTime
                }
               // it.base = SystemClock.elapsedRealtime()
                it.start()
            }
            ChronometerState.Stop->{
                it.stop()
            }
        }
    })
}


@Composable
fun Chronometer(
    resolution: Int,
    textSize: TextUnit,
    lineSize: Dp,
    lineMaxSize: Dp,
    lineWidth: Dp,
    faceType: FaceType,
    modifier: Modifier = Modifier,
    progressProvider: () -> Float,
    centerDial: Boolean = false,
    dialScrewSize: Dp = 5.dp,
    dialWidth: Dp = 3.dp
) {

    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(modifier = modifier.fillMaxSize()) {
        FaceBackground(
            resolution = resolution,
            textSize = textSize,
            lineSize = lineSize,
            lineMaxSize = lineMaxSize,
            lineWidth = lineWidth,
            faceType = faceType
        )
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawDial(
                    value = progressProvider(),
                    circleColor = surfaceColor,
                    centerDial = centerDial,
                    dialScrewSize = dialScrewSize,
                    dialWidth = dialWidth,
                    dialColor = Color.Black
                )
            }
        )
    }

}


fun DrawScope.drawDial(
    value: Float,
    circleColor: Color,
    centerDial: Boolean,
    dialScrewSize: Dp,
    dialWidth: Dp,
    dialColor: Color,
) {
    rotate(value) {

        val start = if (centerDial) size.center else size.center.copy(y = size.height * 0.6f)

        drawLine(
            color = dialColor,
            start = start,
            end = Offset(size.width / 2, size.height * 0.05f),
            strokeWidth = dialWidth.toPx(),
            cap = StrokeCap.Round
        )

        drawCircle(circleColor, dialScrewSize.toPx(), size.center)
        drawCircle(dialColor, dialScrewSize.toPx(), size.center, style = Stroke(width = 2.dp.toPx()))
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun ChronometerPreview() {
    JetpackComposeDemoTheme {
//        Chronometer(
//            modifier = Modifier.aspectRatio(1f),
//            resolution = 1,
//            textSize = 24.sp,
//            lineSize = 10.dp,
//            lineMaxSize = 20.dp,
//            lineWidth = 2.dp,
//            progressProvider = { 0.3f },
//            faceType = FaceType.SECONDS
//        )
        Column(modifier = Modifier.fillMaxWidth()) {
            val chronometerState : MutableState<ChronometerState> = remember {
                mutableStateOf(ChronometerState.Idle)
            }
            AndroidChronometer(modifier = Modifier,chronometerState=chronometerState.value, chronometerOnTick ={

            } )
            Button(onClick = {
                if(chronometerState.value == ChronometerState.Start){
                    chronometerState.value = ChronometerState.Stop
                }else{
                    chronometerState.value = ChronometerState.Start
                }
            }) {
                Text(text = if (chronometerState.value == ChronometerState.Start) "停止" else "开始")
            }
        }
    }
}