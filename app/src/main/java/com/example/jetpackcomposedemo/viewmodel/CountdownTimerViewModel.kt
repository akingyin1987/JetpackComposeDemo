/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 *  倒计时处理数据
 * @Description:
 * @author: aking
 * @CreateDate: 2023/1/4 14:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/1/4 14:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class CountdownTimerViewModel : ViewModel() {

    var countdownSeconds: Int by mutableStateOf(0)

    var page: Page by mutableStateOf(Page.InputScreen)

    val numbers = mutableListOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""
    )
    var fullNumbers: String by mutableStateOf("000000")

    var inputNumbers: String by mutableStateOf("")

    fun inputNumber(number: String) {
        if (inputNumbers.isEmpty() && "0" == number) {
            return
        }
        if (inputNumbers.length < 6) {
            inputNumbers = inputNumbers.plus(number)
            fullNumbers = "000000".removeRange(0, inputNumbers.length).plus(inputNumbers)
        }
    }

    fun removeNumber() {
        val length = inputNumbers.length
        if (length > 0) {
            inputNumbers = inputNumbers.removeRange(length - 1, length)
            fullNumbers = "000000".removeRange(0, inputNumbers.length).plus(inputNumbers)
        }
    }

    fun countdownOrStop() {
        when (page) {
            Page.InputScreen -> {
                if (inputNumbers.isNotEmpty()) {
                    start()
                }
            }
            Page.CountdownScreen -> {
                preStop()
                stop()
            }
        }
    }

    fun start() {
        page = Page.CountdownScreen
        animateFinished = false
    }

    fun preStop() {
        countdownSeconds = 0
        animateFinished = true
    }

    fun stop() {
        page = Page.InputScreen
    }

    fun animateNow() {
        val hour = fullNumbers.substring(0, 2).toInt()
        val minute = fullNumbers.substring(2, 4).toInt()
        val second = fullNumbers.substring(4, 6).toInt()
        countdownSeconds = hour * 60 * 60 + minute * 60 + second
    }

    var animateFinished = false
}

enum class Page {
    InputScreen, CountdownScreen
}
