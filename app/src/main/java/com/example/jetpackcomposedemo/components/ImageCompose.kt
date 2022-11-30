/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.rememberRandomSampleImageUrl

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/11/27 8:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/11/27 8:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Preview
@Composable
fun ImageComposePreview(){
    val context  = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {

        AsyncImage(model = ImageRequest.Builder(context)
            .data(rememberRandomSampleImageUrl())
            .error(R.drawable.placeholder)
            .crossfade(true).build(),
            contentDescription = null,
            error = painterResource(id = R.drawable.placeholder),
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier.fillMaxSize())
    }
}