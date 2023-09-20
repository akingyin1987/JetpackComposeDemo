/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.example.jetpackcomposedemo.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import kotlinx.coroutines.launch

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/9/20 11:18
 * @version: 1.0
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetMainScreen(onBack: () -> Unit) {
	
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
	
	val coroutineScope = rememberCoroutineScope()
	
	
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "BottomSheet") },
				navigationIcon = {
					IconButton(onClick = {
						println("返回事件")
						onBack.invoke()
					}) {
						Icon(Icons.Filled.ArrowBack, null)
					}
				}
			)
		}
	) {
		Box(modifier = Modifier.padding(it)) {
			Button(onClick = {
				if (sheetState.isVisible) {
					coroutineScope.launch {
						sheetState.hide()
					}
				} else {
					coroutineScope.launch {
						sheetState.show()
					}
				}
			}) {
				Text(text = "显示与隐藏=${sheetState.isVisible}")
			}
			
			ModalBottomSheet(
				onDismissRequest = {
				
				},
				sheetState = sheetState,
				
				) {
				Text(text = "这是测试内容")
				LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
					items(100) {
						Text(text = "这是内容=${it}")
					}
				}
			}
			
		}
	}
}


@Preview(name = "bottomSheet")
@Composable
fun BottomSheetPreview() {
	JetpackComposeDemoTheme {
		BottomSheetMainScreen {
		
		}
	}
}