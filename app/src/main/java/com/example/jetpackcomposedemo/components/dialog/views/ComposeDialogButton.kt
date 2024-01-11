/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.dialog.views

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.jetpackcomposedemo.components.dialog.DialogButton
import com.example.jetpackcomposedemo.components.dialog.DialogButtonType
import com.example.jetpackcomposedemo.components.dialog.DialogEvent
import com.example.jetpackcomposedemo.components.dialog.DialogState
import com.example.jetpackcomposedemo.components.dialog.Options

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2024/1/11 10:31
 * @version: 1.0
 */

@Composable
fun ComposeDialogButton(
    button: DialogButton,
    buttonType: DialogButtonType,
    options: Options,
    state: DialogState,
    requestDismiss: () -> Unit,
    onEvent: (event: DialogEvent) -> Unit
) {
    if (button.text.isNotEmpty()) {
        val enabled = state.isButtonEnabled(buttonType)
        TextButton(
            enabled = enabled,
            onClick = {
                val dismiss = options.dismissOnButtonClick && state.interactionSource.dismissAllowed.value
                state.onButtonPressed(onEvent, buttonType, dismiss)
                if (dismiss)
                    requestDismiss()
            }) {
            Text(button.text)
        }
    }
}