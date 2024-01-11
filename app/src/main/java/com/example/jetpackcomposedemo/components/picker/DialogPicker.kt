/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.example.jetpackcomposedemo.components.picker

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2024/1/11 11:26
 * @version: 1.0
 */

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ShowPickerDialogPreview() {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val listData = remember {
        List(10){
            TestData().apply {
                name = "选项${it}"
                nextList = List(5){index->
                    TestData().apply {
                        name = "选项${it}->${index}"
                    }
                }
            }
        }
    }
    JetpackComposeDemoTheme {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    showDialog.value = true
                }) {
                    Text(text = "showDialog")
                }
            }
        }
        if (showDialog.value) {
          PickerDialog(
              onDismiss = { showDialog.value = false },
              listSelectData = listData,
              onSelectValue = { firstValue, secondValue ->

              },
              defaultSelectFirstValue = TestData().apply { name ="选项4" }
          )
        }
    }
}




@Composable
private fun <T:IPickerData<T>> PickerDialog(onDismiss: () -> Unit, listSelectData:List<T>,onSelectValue:(firstValue:T,secondValue:T?)->Unit,defaultSelectFirstValue:T?= null,defaultSecondValue:T?=null) {



    val selectFirstValue = remember {
        mutableStateOf(defaultSelectFirstValue)
    }
    val selectSecondValue = remember {
        mutableStateOf(defaultSecondValue)
    }

    val secondListData = remember {
       mutableStateOf(emptyList<T>())
    }
    val listState1 = rememberLazyListState()
    val listState2 = rememberLazyListState()

    LaunchedEffect(Unit){
        defaultSelectFirstValue?.let {
            listSelectData.indexOf(defaultSelectFirstValue).let { index->
                if(index>3){
                    listState1.animateScrollToItem(index)
                }
                secondListData.value = listSelectData.getOrNull(index)?.getNextPickerListData()?: emptyList()
            }


            defaultSecondValue?.let { second->
               secondListData.value.indexOf(second).let {index->
                   if(index>3){
                       listState2.animateScrollToItem(index)
                   }
               }
            }
        }

    }
    LaunchedEffect(selectFirstValue.value){
        if(null != selectFirstValue.value){
            listSelectData.indexOf(selectFirstValue.value).let { index->
                secondListData.value = listSelectData.getOrNull(index)?.getNextPickerListData()?: emptyList()
            }
        }

    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()) {
                Text(
                    text = "请选择特殊类型",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row (
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextButton(onClick = onDismiss) {
                        Text(text = "取消")
                    }

                    TextButton(onClick = {
                        selectFirstValue.value?.let {
                            onSelectValue.invoke(it,selectSecondValue.value)
                        }
                        onDismiss()

                    }) {
                        Text(text = "确定")
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth()
                        .height(260.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    LazyColumn(
                        state = listState1,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .weight(1F)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(listSelectData) { value ->
                            if (value == selectFirstValue.value) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                ) {
                                    TextButton(
                                        onClick = {  },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = value.toString())
                                    }
                                }
                            } else {
                                TextButton(
                                    onClick = { selectFirstValue.value = value },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = value.toString())
                                }
                            }

                        }
                    }

                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                    )

                    LazyColumn(
                        state = listState2,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .weight(1F)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(secondListData.value) { value ->
                            if (value == selectSecondValue.value) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                ) {
                                    TextButton(
                                        onClick = { },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = value.toString())
                                    }
                                }
                            } else {
                                TextButton(
                                    onClick = {
                                        selectSecondValue.value = value
                                    }, modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = value.toString())
                                }
                            }

                        }
                    }


                }
            }

        }

    }

}