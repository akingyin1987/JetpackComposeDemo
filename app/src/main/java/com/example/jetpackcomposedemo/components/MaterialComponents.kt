package com.example.jetpackcomposedemo.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 *Material 组件和布局
 * @Description:
 * @author: aking
 * @CreateDate: 2022/8/18 16:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/8/18 16:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


@Composable
fun MyApp(){

    Column(modifier = Modifier.padding(3.dp)) {
        val clickNumber = remember {
            mutableStateOf(0)
        }
        Button(onClick = {
            clickNumber.value = clickNumber.value+1
        },
            //按钮正视图高度，类似阴影这种效果，体现层次感
            elevation=ButtonDefaults.buttonElevation(0.dp) ,
            contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp),
            //圆角
            shape = RoundedCornerShape(2.dp),

        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Like ${clickNumber.value}")
        }
    }
}



@Preview
@Composable
fun DefaultPreview1() {
  MaterialTheme {
      MyApp()
  }

}
