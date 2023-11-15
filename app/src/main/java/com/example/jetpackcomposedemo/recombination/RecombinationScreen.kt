/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.example.jetpackcomposedemo.recombination

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/9/26 16:47
 * @version: 1.0
 */

@Composable
private fun ComposableContainerA(text:String){
	SideEffect {
		Log.d("重组观察","重组作用域A进行了重组")
	}
	Column(
		Modifier
			.background(Color.Black)
			.padding(10.dp)) {
		Text(text = "我是重组作用域A，当前值${text}",color = Color.White)
		ComposableBoxA()
	}
}


/**
 * 有时候，我们需要将Compose状态共享给非Compose管理的对象。此时，Google建议我们使用SideEffect函数，因为每次recompose成功时都会调用该函数。SideEffect特点如下：
 *
 * 在Composition操作失败时，能保证SideEffect中的非Compose管理对象的状态和Composition中的状态一致。
 * SideEffect在LayoutNode被创建并插入layout tree后才会被调用。
 * 以下是使用SideEffect的一个示例。分析库允许通过将自定义元数据（在此示例中为“用户属性”）附加到所有后续分析事件，来细分用户群体。如需将当前用户的用户类型传递给分析库，需要使用 SideEffect 更新其值。
 */
@Composable
private fun ComposableBoxA() {
	SideEffect {
		Log.d("重组观察", "重组作用域A内部的容器进行了重组")
	}
	Text("我是A容器的内部组件", color = Color.White, modifier = Modifier.background(Color.Gray))
}
@Composable
private fun ComposableContainerB(text: String){
	SideEffect {
		Log.d("重组观察","重组作用域B进行了重组")
	}
	Column(
		Modifier
			.background(Color.Red)
			.padding(10.dp)) {
		Text(text = "我是重组作用域B，当前值${text}",color = Color.White)
		ComposableBoxA()
	}
}


@Composable
fun ClickCounter(){
	
	var  clicks  by remember {
		mutableIntStateOf(0)
	}
	Column(modifier = Modifier.fillMaxWidth()) {
		Button(onClick =  {
			clicks++
		}) {
			
			Text(text ="I've been clicked 1 $clicks times" ).apply {
				println("重组 3")
			}
		}
		Button(onClick =  {
			clicks++
		}) {
			
			Text(text ="I've been clicked 2 $clicks times" ).apply {
				println("重组 2")
			}
		}
		Text(text = "click=${clicks}").apply {
			println("重组 1")
		}
		Text(text = "123").apply {
			println("重组 123")
		}
	}
	
}


@Preview
@Composable
fun ComposePreview2(){
	val listState = rememberLazyListState()
	val showButton by remember {
		derivedStateOf { 
			listState.firstVisibleItemIndex>0
		}
	}
	val list = List(100){index ->
		"index=${index}"
	}
	
	Box(modifier = Modifier.fillMaxSize()){
		AnimatedVisibility(visible = showButton, modifier = Modifier.align(Alignment.TopStart)) {
			FloatingActionButton(onClick = { }) {
				Icon(imageVector = Icons.Filled.Add, contentDescription = null)
			}
		}
		
		AnimatedVisibility(visible = !listState.isScrollInProgress, modifier = Modifier.align(
			Alignment.TopEnd)) {
			FloatingActionButton(onClick = { }) {
				Icon(imageVector = Icons.Filled.Add, contentDescription = null)
			}
		}
		
		LazyColumn(state =listState) {
			items(list){
				Log.d("重组观察","重组项=${it}")
				Text(text = it, modifier = Modifier.fillMaxWidth())
			}
		}
	}
	
}

@Preview
@Composable
fun ComposePreview(){
	var valueA by remember { mutableIntStateOf(0) }
	var valueB by remember { mutableIntStateOf(0) }
	
	SideEffect {
		Log.d("重组观察","最外层容器进行了重组")
	}
	
	Column {
		ComposableContainerA(text = "$valueA")
		ComposableContainerB(text = "$valueB")
		Row {
			Button(onClick = { valueA++ }) {
				Text("A值加1")
			}
			
			Button(onClick = { valueB++ }) {
				Log.d("重组","Button B")
				Text("B值加1")
			}
		}
		Log.d("重组观察","Column")
	}
	
}