package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *  列表
 * @Description:
 * @author: aking
 * @CreateDate: 2022/7/27 11:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/7/27 11:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class LazyListCompose {

    @Composable
    fun  listVerticalCompose(){

        val  listData = remember {
            mutableStateOf<List<String>>(emptyList())
        }
        Row {
           LazyColumn(modifier = Modifier.border(border = BorderStroke(3.dp,Color.Black)).fillMaxWidth()){
               item {

                   Text(text = "点击新增", modifier = Modifier.clickable {
                       listData.value = listData.value.toMutableList().apply {
                           add("添加第${size}项")
                       }
                   }.height(40.dp).fillMaxWidth().padding(3.dp).align(alignment = Alignment.CenterVertically), textAlign = TextAlign.Center)
               }
               items(listData.value.size){ index ->
                   Text(text = "第$index 项,value=${listData.value[index]}", modifier = Modifier.border(border = BorderStroke(3.dp,Color.Black)).fillMaxWidth().padding(5.dp)

                       .clickable {
                           listData.value = listData.value.toMutableList().also {
                               it.removeAt(index)
                           }
                       }
                   , textAlign = TextAlign.Center)
               }
           }
        }
    }


    @Composable
    fun  ListHorizontalCompose(){

        val  listData = remember {
            mutableStateOf<List<String>>(emptyList())
        }
        Row {
            LazyColumn(modifier = Modifier.border(border = BorderStroke(3.dp,Color.Black)).fillMaxWidth()){
                item {

                    Text(text = "点击新增", modifier = Modifier.clickable {
                        listData.value = listData.value.toMutableList().apply {
                            add("添加第${size}项")
                        }
                    }.height(40.dp).fillMaxWidth().padding(3.dp).align(alignment = Alignment.CenterVertically), textAlign = TextAlign.Center)
                }
                items(listData.value.size){ index ->
                    Text(text = "第$index 项,value=${listData.value[index]}", modifier = Modifier.border(border = BorderStroke(3.dp,Color.Black)).fillMaxWidth().padding(5.dp)

                        .clickable {
                            listData.value = listData.value.toMutableList().also {
                                it.removeAt(index)
                            }
                        }
                        , textAlign = TextAlign.Center)
                }
            }
        }
    }


    @Preview
    @Composable
    fun DefaultPreview(){

        ListHorizontalCompose()
    }
}