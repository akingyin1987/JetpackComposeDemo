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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * 条状标签
 * @Description:
 * @author: aking
 * @CreateDate: 2022/11/30 10:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/11/30 10:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipComposePreview(){
    JetpackComposeDemoTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            var enabled by remember {
                mutableStateOf(true)
            }
            var selected by remember {
                mutableStateOf(true)
            }


            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    enabled = !enabled

                }) {
                    Text(text = "setEnable")
                }

                Button(onClick = {
                    selected = !selected

                }) {
                    Text(text = "setSelect")
                }
                AssistChip(onClick = { /*TODO*/ }, enabled = enabled,
                    label = {
                        Text(text = "AssistChip")
                    }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.IosShare, contentDescription =null )
                    })

                ElevatedAssistChip(onClick = { /*TODO*/ },enabled=enabled,
                    label = {
                        Text(text = "ElevatedAssistChip")
                    }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.IosShare, contentDescription =null )
                    })

                FilterChip(onClick = { /*TODO*/ },enabled=enabled, selected = selected,
                    label = {
                        Text(text = "ElevatedAssistChip")
                    }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.IosShare, contentDescription =null )
                    })

                ElevatedFilterChip(onClick = { /*TODO*/ },enabled=enabled, selected = selected,
                    label = {
                        Text(text = "ElevatedFilterChip")
                    }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }, trailingIcon = {
                        Icon(imageVector = Icons.Default.IosShare, contentDescription =null )
                    })


                InputChip(selected = selected, onClick = { /*TODO*/ },enabled=enabled, label = {
                    Text(text = "InputChip")
                }, leadingIcon = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }, trailingIcon = {
                    Icon(imageVector = Icons.Default.IosShare, contentDescription =null )
                })

                SuggestionChip( onClick = { /*TODO*/ },enabled=enabled, label = {
                    Text(text = "SuggestionChip")
                })
                ElevatedSuggestionChip( onClick = { /*TODO*/ },enabled=enabled, label = {
                    Text(text = "ElevatedSuggestionChip")
                })

            }

        }
    }

}