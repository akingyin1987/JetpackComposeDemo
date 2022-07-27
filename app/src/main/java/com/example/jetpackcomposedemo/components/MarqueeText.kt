package com.example.jetpackcomposedemo.components


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 *
 * android 跑马灯效果
 * @Description:
 * @author: aking
 * @CreateDate: 2022/7/25 12:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/7/25 12:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

enum class MarqueeLayers { MainText, SecondaryText, EdgesGradient }

/**
 * 文字布局信息
 * @property textWidth Int 文本宽度
 * @property containerWidth Int 容器宽度
 * @constructor
 */
data class TextLayoutInfo(val textWidth: Int, val containerWidth: Int)


/**
 * 渐变侧边
 */
@Composable
fun GradientEdge(
    startColor: Color, endColor: Color,
) {
    Box(
        modifier = Modifier
            .width(10.dp)
            .fillMaxHeight()
            .background(
                brush = Brush.horizontalGradient(
                    0f to startColor, 1f to endColor,
                )
            )
    )
}

@Composable
fun MarqueeText(text: String,
                modifier: Modifier = Modifier,
                textModifier: Modifier = Modifier,
                gradientEdgeColor: Color = Color.White,
                color: Color = Color.Unspecified,
                fontSize: TextUnit = TextUnit.Unspecified,
                fontStyle: FontStyle? = null,
                fontWeight: FontWeight? = null,
                fontFamily: FontFamily? = null,
                letterSpacing: TextUnit = TextUnit.Unspecified,
                textDecoration: TextDecoration? = null,
                textAlign: TextAlign? = null,
                lineHeight: TextUnit = TextUnit.Unspecified,
                overflow: TextOverflow = TextOverflow.Clip,
                softWrap: Boolean = true,
                onTextLayout: (TextLayoutResult) -> Unit = {},
                style: TextStyle = LocalTextStyle.current){


    /** 创建 Text View 控件 */
    val createText = @Composable{ localModifier: Modifier ->
        Text(
            text,
            textAlign = textAlign,
            modifier = localModifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = 1,
            onTextLayout = onTextLayout,
            style = style,
        )

    }

    var offset  by remember { mutableStateOf(0) }

    val textLayoutInfoState  = remember {
        mutableStateOf<TextLayoutInfo?>(null)
    }



    //监听当前值 的变化
    LaunchedEffect(textLayoutInfoState.value){
        val textLayoutInfo = textLayoutInfoState.value?:return@LaunchedEffect
        if(textLayoutInfo.textWidth <= textLayoutInfo.containerWidth){
            //如果当前文本内容 未超过当前容器宽度
            return@LaunchedEffect
        }
        if(textLayoutInfo.containerWidth == 0){
            //当前容器宽度不可设置成0
            return@LaunchedEffect
        }
        //计算播放一偏的总时间
        val duration  = 7500 * textLayoutInfo.textWidth/textLayoutInfo.containerWidth

        val delay = 1000L
        do {
            // 定义动画，文字偏移量从0到-文本宽度
            val animation = TargetBasedAnimation(
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = duration,
                        delayMillis = 1000,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                typeConverter = Int.VectorConverter,
                initialValue = 0,
                targetValue = -textLayoutInfo.textWidth
            )
            // 根据动画帧时间，获取偏移量值。
            // 起始帧时间
            val startTime = withFrameNanos { it }
            do {
                val playTime = withFrameNanos { it } - startTime
                offset = animation.getValueFromNanos(playTime)
            } while (!animation.isFinishedFromNanos(playTime))
            // 延迟重新播放
            delay(delay)

        }while (true)

    }
  
    SubcomposeLayout(modifier = modifier.clipToBounds()){ constraints ->
        // 测量文本总宽度
        val infiniteWidthConstraints = constraints.copy(maxWidth = Int.MAX_VALUE)
        var mainText = subcompose(MarqueeLayers.MainText) {
            createText(textModifier)
        }.first().measure(infiniteWidthConstraints)

        var gradient:Placeable?= null

        var secondPlaceableWithOffset : Pair<Placeable,Int>?= null

        if(mainText.width <= constraints.maxWidth){
            //文本宽度 小于容器最大宽度，则无需要跑马灯
            mainText = subcompose(MarqueeLayers.SecondaryText){
                createText(textModifier.fillMaxWidth())
            }.first().measure(constraints)
            textLayoutInfoState.value = null
        }else{
            //循环文本增加间隔时间
            val spacing = constraints.maxWidth*2/3
            textLayoutInfoState.value = TextLayoutInfo(textWidth = mainText.width+spacing, containerWidth = constraints.maxWidth)

            //第二遍文本偏移量
            val secondTextOffset = mainText.width + offset + spacing
            val secondTextSpace = constraints.maxWidth - secondTextOffset

            if(secondTextSpace > 0){
                secondPlaceableWithOffset = subcompose(MarqueeLayers.SecondaryText){
                    createText(textModifier)
                }.first().measure(infiniteWidthConstraints) to secondTextOffset
            }
            gradient = subcompose(MarqueeLayers.EdgesGradient){
                Row {
                    GradientEdge(gradientEdgeColor, Color.Transparent)
                    Spacer(Modifier.weight(1f))
                    GradientEdge(Color.Transparent, gradientEdgeColor)

                }
            }.first().measure(constraints.copy(maxHeight = mainText.height))
        }

        // 将文本、渐变控件 进行位置布局
        layout(
            width = constraints.maxWidth,
            height = mainText.height
        ) {
            mainText.place(offset, 0)
            secondPlaceableWithOffset?.let {
                it.first.place(it.second, 0)
            }
            gradient?.place(0, 0)
        }

    }




    
}
