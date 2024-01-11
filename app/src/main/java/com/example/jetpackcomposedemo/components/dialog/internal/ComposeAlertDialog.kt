/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.dialog.internal

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2024/1/11 10:35
 * @version: 1.0
 */

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.jetpackcomposedemo.components.dialog.DialogButtonType
import com.example.jetpackcomposedemo.components.dialog.DialogButtons
import com.example.jetpackcomposedemo.components.dialog.DialogEvent
import com.example.jetpackcomposedemo.components.dialog.DialogState
import com.example.jetpackcomposedemo.components.dialog.DialogStyle
import com.example.jetpackcomposedemo.components.dialog.Options
import com.example.jetpackcomposedemo.components.dialog.SpecialOptions
import com.example.jetpackcomposedemo.components.dialog.copied.DialogMaxWidth
import com.example.jetpackcomposedemo.components.dialog.copied.DialogMinWidth
import com.example.jetpackcomposedemo.components.dialog.views.ComposeDialogButton

import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

// Wrapper around Dialog because of resizing issues after initial compose:
// Github: https://stackoverflow.com/questions/71285843/how-to-make-dialog-re-measure-when-a-child-size-changes-dynamically/71287474
// Issue: https://issuetracker.google.com/issues/221643630

internal val DialogHorizontalMinMargin = 16.dp
internal val DialogVerticalMinMargin = DialogHorizontalMinMargin

@Composable
fun ComposeAlertDialog(
    title: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    style: DialogStyle.Dialog,
    buttons: DialogButtons,
    options: Options,
    specialOptions: SpecialOptions,
    state: DialogState,
    onEvent: (event: DialogEvent) -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val modifierSwipeDismiss = getSwipeDismissModifier(style, state, onEvent)
    ComposeAlertDialog(
        style,
        onDismissRequest = {
            state.dismiss(onEvent)
        },
        confirmButton = {
            ComposeDialogButton(
                buttons.positive,
                DialogButtonType.Positive,
                options,
                state,
                requestDismiss = {
                    state.dismiss()
                },
                onEvent
            )
        },
        Modifier
            .wrapContentHeight()
            .then(modifierSwipeDismiss),
        dismissButton = if (buttons.negative.text.isNotEmpty()) {
            {
                ComposeDialogButton(
                    buttons.negative,
                    DialogButtonType.Negative,
                    options,
                    state,
                    requestDismiss = {
                        state.dismiss()
                    },
                    onEvent
                )
            }
        } else null,
        icon = icon,
        title = title,
        text = {
            val scrollModifier =  if (options.wrapContentInScrollableContainer) {
                Modifier
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            } else Modifier
            Column(
                modifier = Modifier
                    .then(scrollModifier)
                    .sizeIn(minWidth = DialogMinWidth, maxWidth = DialogMaxWidth)
                    .then(
                        if (specialOptions.dialogIntrinsicWidthMin) {
                            Modifier .width(IntrinsicSize.Min)
                        } else Modifier
                    ),
                //horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun getSwipeDismissModifier(
    style: DialogStyle.Dialog,
    state: DialogState,
    onEvent: (event: DialogEvent) -> Unit
): Modifier {
    return if (style.swipeDismissable) {
        val swipeableState = rememberSwipeableState(initialValue = 0)
        val maxSwipeDistance = with(LocalDensity.current) { 96.dp.toPx() }
        val anchors = mapOf(
            -maxSwipeDistance to -1,
            0f to 0,
            maxSwipeDistance to 1
        )

        val coroutineScope = rememberCoroutineScope()

        if (swipeableState.isAnimationRunning && swipeableState.targetValue != 0) {
            if (!state.dismiss(onEvent)) {
                // expand again if dismissing is not allowed
                //SideEffect 将 Compose 状态发布为 非 Compose 代码
                //如果需要与非 Compose 管理的对象共享 Compose 状态，请使用 SideEffect 可组合项，因为每次成功重组都会调用该可组合项
                SideEffect {
                    coroutineScope.launch {
                        swipeableState.animateTo(0)
                    }
                }
            }
        }

        val alpha = 1f - (0.3f * abs(swipeableState.offset.value / maxSwipeDistance))

        Modifier
            .offset { IntOffset(0, swipeableState.offset.value.roundToInt() / 4) }
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Vertical
            )
            .alpha(alpha)
    } else Modifier
}

@Composable
internal fun ComposeAlertDialog(
    style: DialogStyle.Dialog,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null
) {
    val properties = style.dialogProperties
    val (fixedProperties, fixedModifier, fixedModifierInner) = if (properties.usePlatformDefaultWidth) {
        Triple(
            DialogProperties(
                properties.dismissOnBackPress,
                properties.dismissOnClickOutside,
                properties.securePolicy,
                false,
                properties.decorFitsSystemWindows
            ),
            // outer modifier gets paddings only so that dismiss on click outside works as desired
            Modifier
                .sizeIn(minWidth = DialogMinWidth, maxWidth = DialogMaxWidth)
                //.background(Color.Red)
                .padding(horizontal = DialogHorizontalMinMargin, vertical = DialogVerticalMinMargin)
            //.background(Color.Green)
            ,
            Modifier
                .sizeIn(minWidth = DialogMinWidth, maxWidth = DialogMaxWidth)
            //.background(Color.Blue)
            //.padding(horizontal = DialogHorizontalMinMargin, vertical = DialogVerticalMinMargin)
            //.background(Color.Yellow)
        )
    } else {
        Triple(
            properties,
            Modifier,
            Modifier
        )
    }

   com.example.jetpackcomposedemo.components.dialog.copied.AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier
            .wrapContentHeight()
            .then(fixedModifier),
        modifierInner = modifier
            .wrapContentHeight()
            .then(fixedModifierInner),
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = text,
        shape = style.shape,
        containerColor = style.containerColor,
        iconContentColor = style.iconContentColor,
        titleContentColor = style.titleContentColor,
        textContentColor = style.textContentColor,
        tonalElevation = style.tonalElevation,
        properties = fixedProperties
    )
}