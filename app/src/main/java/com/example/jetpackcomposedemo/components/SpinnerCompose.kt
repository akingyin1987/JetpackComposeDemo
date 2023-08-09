/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/8/9 11:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/8/9 11:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SpinnerCompose(
    list: List<T>,
    preselected: T,
    modifier: Modifier = Modifier,
    viewEnable: Boolean = true,
    title:String ,
    onSelectionChanged: (selection: T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    var selected by remember { mutableStateOf(preselected) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = expanded.not() },
        modifier = modifier
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selected.toString(),
            onValueChange = {},
            label = { Text(text = title, fontSize = 10.sp, color = LocalContentColor.current.copy(
                ContentAlpha.disabled)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            enabled = viewEnable,
            shape = TextFieldDefaults.outlinedShape
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            list.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.toString()) },
                    onClick = {
                        expanded = false
                        selected = selectionOption
                        onSelectionChanged.invoke(selectionOption)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}

@Preview
@Composable
fun SpinnerPreview() {
    SpinnerCompose(list = listOf("选项1","选项2","选项3"), preselected = "请选择", title ="选择参数" , onSelectionChanged = {

    })
}