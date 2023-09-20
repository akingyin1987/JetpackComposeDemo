/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.navhost


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.jetpackcomposedemo.components.bottomsheet.BottomSheetMainScreen
import kotlin.random.Random

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/9/12 14:53
 * @version: 1.0
 */


@Composable
fun NavHostSimple() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = "one") {
		composable(route = "one") {
			println("data=${it.arguments},${it}")
			OnePagerCompose(navController = navController)
		}
		composable(route = "two") {
			TowPagerCompose(navController = navController)
		}
		composable(route="bottomSheet"){
			BottomSheetMainScreen {
				println("返回")
				navController.navigateUp()
			}
		}
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnePagerCompose(navController: NavController,viewModel: NavHostViewModel= hiltViewModel() ) {
	val listData = remember {
		mutableStateOf(MutableList(100) {
			"test${it}${Random.nextInt()}"
		}.toList())
	}
	
	val scrollState = rememberScrollState()
	val userState = viewModel.userState.collectAsState()
	
	
	var text by remember { mutableStateOf("") }
	
	LaunchedEffect(userState.value ){
		text = userState.value.userName
	}
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "测试界面1") },
				navigationIcon = {
					IconButton(onClick = {
						navController.popBackStack()
					}) {
						Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
					}
				}
			)
		}
	) {
		Column(
			modifier = Modifier
				.padding(it)
				.fillMaxSize()
				.verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(4.dp)
		) {
			OutlinedTextField(
				value = text,
				onValueChange = {
					text = it
				},
				label = {
					Text(text = "测试")
				}
			)
			listData.value.forEach { data ->
				TextButton(onClick = {
					viewModel.setUserName(text)
					navController.navigate("bottomSheet")
				}) {
					Text(text = data)
				}
			}
			
		}
//		LazyColumn(modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(4.dp)){
//			item {
//				Button(onClick = {
//					navController.navigate("two")
//				}) {
//					Text(text = "进入其它界面")
//				}
//			}
//			items(items = listData.value){data->
//				TextButton(onClick = {
//					navController.navigate("two")
//				}) {
//					Text(text = data)
//				}
//			}
//		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TowPagerCompose(navController: NavController) {
	val listData = remember {
		mutableStateOf(MutableList(100) {
			"test2${it}"
		}.toList())
	}
	
	
	BackHandler {
		if(navController.navigateUp().not()){
			navController.popBackStack()
		}
	}
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(text = "测试界面2") },
				navigationIcon = {
					IconButton(onClick = {
						navController.navigateUp()
					}) {
						Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
					}
				}
			)
		}
	) {
		LazyColumn(modifier = Modifier.padding(it)) {
			items(items = listData.value) { data ->
				Text(text = data)
			}
		}
	}
}