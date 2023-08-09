/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Preview
@Composable
fun ImageCompose2Preview(){
    Column(modifier = Modifier.width(68.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(modifier = Modifier
            .size(52.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.surfaceVariant,
                CircleShape
            )
            .padding(5.dp)
            .clip(CircleShape)
            ,painter = painterResource(id = R.drawable.ic_baseline_account_box_24)
            , contentScale = ContentScale.Crop
            , contentDescription = null)
        Text(text = "微信登录")
    }
}