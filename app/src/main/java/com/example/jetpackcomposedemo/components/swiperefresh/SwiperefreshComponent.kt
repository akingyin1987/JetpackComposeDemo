/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.swiperefresh

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import org.json.JSONObject

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/12/18 20:26
 * @version: 1.0
 */


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun SwipeRefreshComponent() {
    var refreshing by remember { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()

    val json  = remember {
        GsonBuilder().create()
    }

    val screenEnd = remember {
        derivedStateOf {
            val lastItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastItem == null || lastItem.size + lastItem.offset == lazyListState.layoutInfo.viewportEndOffset
        }
    }

   // println("screenEnd.value=${screenEnd.value}")
    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }.collect{
          //  println("firstVisibleItemIndex=$it,firstVisibleItemScrollOffset="+lazyListState.firstVisibleItemScrollOffset)
//            lazyListState.layoutInfo.let {lazyListLayoutInfo ->
//                println("${lazyListLayoutInfo.totalItemsCount}" + ":"+lazyListLayoutInfo.viewportEndOffset+":"+lazyListLayoutInfo.viewportStartOffset)
//            }
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let {lastItem ->
                println("${lastItem.size}" + ":"+lastItem.offset+":"+lazyListState.layoutInfo.viewportEndOffset)
            }
        }
        snapshotFlow {
            lazyListState.firstVisibleItemScrollOffset
        }.collect{
            println("firstVisibleItemScrollOffset=$it")
        }
    }

    LaunchedEffect(screenEnd.value ){
        println("screenEnd.value=${screenEnd.value}")
    }

    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
        }
    )
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }
    JetpackComposeDemoTheme {

        Box(modifier = Modifier.pullRefresh(refreshState)) {
            LazyColumn(state = lazyListState) {
                stickyHeader {
                    Text(text ="header")
                }
                items(30) {
                    Row(Modifier.padding(16.dp)) {
                        Text(
                            text = "Text $it",
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )
                    }


                }


                if(screenEnd.value){
                    item{
                        Text(text = "---------------无数据到底了-----------------")
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}