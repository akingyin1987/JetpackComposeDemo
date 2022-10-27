/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/10/25 11:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/25 11:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

private val rangeForRandom = (0..100000)


fun randomSampleImageUrl(
    seed: Int = rangeForRandom.random(),
    width: Int = 300,
    height: Int = width
): String {
    return "https://picsum.photos/seed/$seed/$width/$height"
}

@Composable
fun rememberRandomSampleImageUrl(
    seed: Int = rangeForRandom.random(),
    width: Int = 300,
    height: Int = width
): String = remember {
    randomSampleImageUrl(seed, width, height)
}

