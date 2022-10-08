package com.example.jetpackcomposedemo.components

import androidx.compose.material.Colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/30 14:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/30 14:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class ArcProgressData {
    var progress:Int = 0
    var max : Int  = 100
    var angle :Float = 0F
    var strokeWidth = 5.dp
    var unfinishedColor = Color(72,106,176)
    var finishedColor = Color.Black
    var textSize  = 16.sp
    var textColor = Color.Black
    var suffixText:String ="%"
    var suffixTextSize = 12.sp
    var suffixTextPadding = 3.dp
    var bottomText  = ""
    var bottomTextSize  = 14.sp

}