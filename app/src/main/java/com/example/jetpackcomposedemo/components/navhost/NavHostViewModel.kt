/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.navhost

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/9/13 9:24
 * @version: 1.0
 */
@HiltViewModel
class NavHostViewModel @Inject constructor():ViewModel() {
	
	val userState = MutableStateFlow(User())
	
	
	fun setUserName(name:String){
		userState.value = User(name)
	}
}