/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.dialog

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2024/1/11 10:33
 * @version: 1.0
 */

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.jetpackcomposedemo.components.dialog.internal.ComposeAlertDialog
import com.example.jetpackcomposedemo.components.dialog.internal.ComposeBottomSheetDialog


// ------------------
// defaults functions
// ------------------

private val SheetPeekHeight =
    56.dp * 2 // we want peek height for content + fixed buttons so with take 56*2

/**
 * Shows a dialog containing some arbitrary [content]
 *
 * @param state the [DialogState] of the dialog
 * @param title the optional title of the dialog (an empty title will not be shown)
 * @param icon the optional icon of the dialog
 * @param style the [DialogStyle] of the dialog - use [DialogDefaults.styleDialog] or [DialogDefaults.styleBottomSheet]
 * @param buttons the [DialogButtons] of the dialog - use [DialogDefaults.buttons] here [DialogDefaults.buttonsDisabled]
 * @param options the [Options] of the dialog
 * @param specialOptions the [SpecialOptions] of the dialog
 * @param onEvent the callback for all [DialogEvent] - this can be a button click [DialogEvent.Button] or the dismiss information [DialogEvent.Dismissed]
 * @param content the content of this dialog
 */
@Composable
fun Dialog(
    state: DialogState,
    title: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    style: DialogStyle = DialogDefaults.styleDialog(),
    buttons: DialogButtons = DialogDefaults.buttons(),
    options: Options = Options(),
    specialOptions: SpecialOptions = SpecialOptions(),
    onEvent: (event: DialogEvent) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    when (style) {
        is DialogStyle.BottomSheet -> {
            ComposeBottomSheetDialog(
                title,
                icon,
                style,
                buttons,
                options,
                specialOptions,
                state,
                onEvent,
                content
            )
        }

        is DialogStyle.Dialog -> {
            ComposeAlertDialog(
                title,
                icon,
                style,
                buttons,
                options,
                specialOptions,
                state,
                onEvent,
                content
            )
        }
    }
}

// ------------------
// defaults functions
// ------------------

object DialogDefaults {

    /**
     * the setup of a dialog that shows as a normal dialog popup
     *
     * @param swipeDismissable if true, the dialog can be swiped away by an up/down swipe
     * @param dismissOnBackPress if true, the dialog can be dismissed by a back press
     * @param dismissOnClickOutside if true, the dialog can be dismissed by clicking outside of its borders
     * @param securePolicy the [SecureFlagPolicy]
     * @param usePlatformDefaultWidth if true, platform default width is used
     * @param decorFitsSystemWindows if true, decor will set the fitsSystemWindows flag
     * @param shape the [Shape] of the dialog
     * @param containerColor the [Color] of the container
     * @param iconContentColor the content [Color] of the icon
     * @param titleContentColor the content [Color] of the title
     * @param textContentColor the content [Color] of the text
     * @param tonalElevation the elevation for the tonal [Color]
     */
    @Composable
    fun styleDialog(
        swipeDismissable: Boolean = false,
        // DialogProperties
        dismissOnBackPress: Boolean = true,
        dismissOnClickOutside: Boolean = true,
        securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
        usePlatformDefaultWidth: Boolean = true,
        decorFitsSystemWindows: Boolean = true,
        // AlertDialog Settings
        shape: Shape = AlertDialogDefaults.shape,
        containerColor: Color = AlertDialogDefaults.containerColor,
        iconContentColor: Color = AlertDialogDefaults.iconContentColor,
        titleContentColor: Color = AlertDialogDefaults.titleContentColor,
        textContentColor: Color = AlertDialogDefaults.textContentColor,
        tonalElevation: Dp = AlertDialogDefaults.TonalElevation
    ) = DialogStyle.Dialog(
        swipeDismissable,
        // DialogProperties
        dismissOnBackPress,
        dismissOnClickOutside,
        securePolicy,
        usePlatformDefaultWidth,
        decorFitsSystemWindows,
        // AlertDialog Settings
        shape,
        containerColor,
        iconContentColor,
        titleContentColor,
        textContentColor,
        tonalElevation
    )

    /**
     * the setup of a dialog that shows as a normal dialog popup
     *
     * @param dragHandle if true, a drag handle will be shown
     * @param hideAnimated if true, hiding the bottom sheet will be animated
     * @param resizeContent if true, the content of the bottom sheet will be resized
     * @param peekHeight the peek height of the bottom sheet
     * @param dismissOnBackPress if true, the dialog can be dismissed by a back press
     * @param dismissOnClickOutside if true, the dialog can be dismissed by clicking outside of its borders
     * @param securePolicy the [SecureFlagPolicy]
     * @param decorFitsSystemWindows if true, decor will set the fitsSystemWindows flag
     */
    @Composable
    fun styleBottomSheet(
        dragHandle: Boolean = true,
        hideAnimated: Boolean = false,
        resizeContent: Boolean = false,
        peekHeight: Dp = SheetPeekHeight,
        // DialogProperties
        dismissOnBackPress: Boolean = true,
        dismissOnClickOutside: Boolean = true,
        securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
        //usePlatformDefaultWidth: Boolean = true,
        decorFitsSystemWindows: Boolean = true
    ) = DialogStyle.BottomSheet(
        dragHandle,
        hideAnimated,
        resizeContent,
        peekHeight,
        // DialogProperties
        dismissOnBackPress,
        dismissOnClickOutside,
        securePolicy,
        //usePlatformDefaultWidth,
        decorFitsSystemWindows
    )

    /**
     * the setup of the dialog buttons
     *
     * @param positive positive [DialogButton]
     * @param negative negative [DialogButton]
     */
    @Composable
    fun buttons(
        positive: DialogButton = DialogButton(stringResource(android.R.string.ok)),
        negative: DialogButton = DialogButton("")
    ) = DialogButtons(
        positive,
        negative
    )

    /**
     * the setup of the dialog buttons if you do not want to show any buttons
     */
    @Composable
    fun buttonsDisabled() = DialogButtons.DISABLED
}

// ------------------
// classes
// ------------------

/**
 * see [DialogDefaults.styleDialog] and [DialogDefaults.styleBottomSheet]
 */
sealed class DialogStyle {

    /**
     * see [DialogDefaults.styleDialog]
     */
    class Dialog internal constructor(
        val swipeDismissable: Boolean,
        // DialogProperties
        dismissOnBackPress: Boolean,
        dismissOnClickOutside: Boolean,
        securePolicy: SecureFlagPolicy,
        usePlatformDefaultWidth: Boolean,
        decorFitsSystemWindows: Boolean,
        // AlertDialog Settings
        val shape: Shape,
        val containerColor: Color,
        val iconContentColor: Color,
        val titleContentColor: Color,
        val textContentColor: Color,
        val tonalElevation: Dp
    ) : DialogStyle() {

        internal val dialogProperties = DialogProperties(
            dismissOnBackPress,
            dismissOnClickOutside,
            securePolicy,
            usePlatformDefaultWidth,
            decorFitsSystemWindows
        )
    }

    /**
     * see [DialogDefaults.styleBottomSheet]
     */
    class BottomSheet internal constructor(
        val dragHandle: Boolean,
        val hideAnimated: Boolean,
        val resizeContent: Boolean,
        val peekHeight: Dp,
        // DialogProperties
        dismissOnBackPress: Boolean,
        dismissOnClickOutside: Boolean,
        securePolicy: SecureFlagPolicy,
        //usePlatformDefaultWidth: Boolean,
        decorFitsSystemWindows: Boolean,
    ) : DialogStyle() {
        @OptIn(ExperimentalComposeUiApi::class)
        internal val dialogProperties = DialogProperties(
            dismissOnBackPress,
            dismissOnClickOutside,
            securePolicy,
            false,//usePlatformDefaultWidth,
            decorFitsSystemWindows
        )
    }

    // Popup?
    // Fullscreen?
}

/**
 * the sealed event for all dialog events (button click and dismissal)
 *
 * [DialogEvent.dismissed] the information if this event will dismiss the dialog or not
 * [DialogEvent.isPositiveButton] a convenient value to determine, if this is the event of the positive dialog button
 */
sealed class DialogEvent {
    abstract val dismissed: Boolean
    abstract val isPositiveButton: Boolean // most interesting attribute so we make it easily accessible

    /**
     * the event of a dialog button click
     *
     * @param button the [DialogButtonType] of the button that was clicked
     * @param dismissed the information if this button will dismiss the dialog or not
     */
    data class Button(val button: DialogButtonType, override val dismissed: Boolean) :
        DialogEvent() {
        override val isPositiveButton = button == DialogButtonType.Positive
    }

    /**
     * the event of a dialog dismissal
     */
    object Dismissed : DialogEvent() {
        override val dismissed = true
        override val isPositiveButton = false
        override fun toString() = "DialogEvent::Dismissed"
    }
}

/**
 * an enum for the different dialog button types
 */
enum class DialogButtonType {
    Positive,
    Negative
}

/**
 * an interaction source for button states and option state
 *
 * @param buttonPositiveEnabled the positive button is only enabled if this state is true
 * @param buttonNegativeEnabled the negative button is only enabled if this state is true
 * @param dismissAllowed the dialog can only be dismissed if this state is true
 * @param swipeAllowed the dialog can only be swiped away if this state is true
 */
class DialogInteractionSource internal constructor(
    val buttonPositiveEnabled: MutableState<Boolean>,
    val buttonNegativeEnabled: MutableState<Boolean>,
    val dismissAllowed: MutableState<Boolean>,
    val swipeAllowed: MutableState<Boolean>
)

/**
 * a dialog state holding the current showing state and the some additional state [DialogInteractionSource] of the dialog
 *
 * @param showing the showing state of the dialog
 * @param interactionSource the [DialogInteractionSource] holding other states for this dialog
 */
open class DialogState internal constructor(
    showing: MutableState<Boolean>,
    interactionSource: MutableState<DialogInteractionSource>
) {

    var showing by showing
    var interactionSource by interactionSource

    /**
     * this will show this dialog
     */
    fun show() {
        showing = true
    }

    /**
     * this will dismiss this dialog (if the [interactionSource] ([DialogInteractionSource.dismissAllowed]) does allow this)
     *
     * @return true, if the dismiss was successful
     */
    fun dismiss(): Boolean {
        if (interactionSource.dismissAllowed.value) {
            showing = false
        }
        return !showing
    }

    internal fun dismiss(
        onEvent: (event: DialogEvent) -> Unit
    ): Boolean {
        if (dismiss())
            onEvent(DialogEvent.Dismissed)
        return !showing
    }

    internal fun onButtonPressed(
        onEvent: (event: DialogEvent) -> Unit,
        button: DialogButtonType,
        dismiss: Boolean
    ) {
        onEvent(DialogEvent.Button(button, dismiss))
    }

    /**
     * this will determine if a button currently can be pressed (depends on the [interactionSource] ([DialogInteractionSource.buttonPositiveEnabled] or [DialogInteractionSource.buttonNegativeEnabled]))
     */
    fun isButtonEnabled(button: DialogButtonType): Boolean {
        return when (button) {
            DialogButtonType.Positive -> interactionSource.buttonPositiveEnabled.value
            DialogButtonType.Negative -> interactionSource.buttonNegativeEnabled.value
        }
    }

    /**
     * this will enable or disable a button
     *
     * @param button the [DialogButtonType] that should be enabled/disabled
     * @param enabled if true, the button will be enabled
     */
    fun enableButton(
        button: DialogButtonType,
        enabled: Boolean
    ) {
        when (button) {
            DialogButtonType.Positive -> interactionSource.buttonPositiveEnabled.value = enabled
            DialogButtonType.Negative -> interactionSource.buttonNegativeEnabled.value = enabled
        }
    }

    /**
     * this will make the dialog dismissable or not
     *
     * @param enabled if true, the dialog can be dismissed
     */
    fun dismissable(enabled: Boolean) {
        interactionSource.dismissAllowed.value = enabled
    }
}

/**
 * a dialog state holding the current showing state and the some additional state [DialogInteractionSource] of the dialog
 *
 * in this case, the showing state is derived from the holded data state and if state holds some data this means, that this dialog is visible
 *
 * @param showing the showing state of the dialog
 * @param d the data state
 * @param interactionSource the [DialogInteractionSource] holding other states for this dialog
 */
class DialogStateWithData<T : Any> internal constructor(
    showing: MutableState<Boolean>,
    private val d: MutableState<T?>,
    interactionSource: MutableState<DialogInteractionSource>
) : DialogState(showing, interactionSource) {

    val data: T?
        get() = d.value

    /**
     * this will return the currently holded data
     *
     * should only be called if the dialog is shown because of a forcefully cast to a non null value!
     */
    fun requireData() = d.value!!

    /**
     * this will show this dialog
     */
    fun show(data: T) {
        this.d.value = data
        show()
    }
}

/**
 * a dialog buttons
 *
 * @param text the text of the button
 */
data class DialogButton(
    val text: String
)

/**
 * see [DialogDefaults.buttons] and [DialogDefaults.buttonsDisabled]
 */
data class DialogButtons internal constructor(
    val positive: DialogButton,
    val negative: DialogButton
) {
    companion object {
        val DISABLED = DialogButtons(DialogButton(""), DialogButton(""))
    }
}

/**
 * the main options for a dialog
 *
 * @param dismissOnButtonClick if true, the dialog will be automatically dismissed if a dialog button is clicked
 * @param wrapContentInScrollableContainer if true, the dialog will wrap its content inside a scrollable container
 */
data class Options(
    val dismissOnButtonClick: Boolean = true,
    val wrapContentInScrollableContainer: Boolean = false
)

/**
 * the special options for a dialog
 *
 * @param dialogIntrinsicWidthMin if true, the dialog width will use [IntrinsicSize.Min]
 */
data class SpecialOptions(
    val dialogIntrinsicWidthMin: Boolean = false
)