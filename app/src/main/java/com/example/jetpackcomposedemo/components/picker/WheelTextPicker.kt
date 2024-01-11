/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.picker

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2024/1/9 18:14
 * @version: 1.0
 */


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

@Composable
fun WheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    size: DpSize = DpSize(128.dp, 128.dp),
    texts: List<String>,
    rowCount: Int,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
) {
    WheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        size = size,
        count = texts.size,
        rowCount = rowCount,
        selectorProperties = selectorProperties,
        onScrollFinished = onScrollFinished
    ){ index ->
        Text(
            text = texts[index],
            style = style,
            color = color,
            maxLines = 1
        )
    }
}


@Preview
@Composable
fun WheelPickerPreview(){
    val listData = remember {
        List(20){
           "index=${it}"
        }
    }
    val selectValue = remember {
        mutableStateOf("")
    }
    val lazyListState1 = rememberLazyListState()
    val lazyListState2 = rememberLazyListState()
  JetpackComposeDemoTheme {
      Surface {
          Row (modifier = Modifier
              .fillMaxWidth()
              .height(100.dp), verticalAlignment = Alignment.CenterVertically){
              LazyColumn(
                 modifier = Modifier.weight(1F),
                 state = lazyListState1,
                 horizontalAlignment = Alignment.CenterHorizontally
              ){
                  items(listData){
                      Text(text = "value=${it}")
                  }
              }
              Divider(
                 modifier = Modifier.padding(horizontal = 2.dp)
                     .width(1.dp)
                     .fillMaxHeight()
              )
              LazyColumn(
                  modifier = Modifier.weight(1F),
                  state = lazyListState2,
                  horizontalAlignment = Alignment.CenterHorizontally,
                  contentPadding = PaddingValues(vertical = 4.dp)
              ){
                  items(listData){
                      Text(text = "value2=${it}")
                  }
              }

          }
      }
  }
}