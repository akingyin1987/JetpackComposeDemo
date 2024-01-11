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
 * @since: 2024/1/11 10:58
 * @version: 1.0
 */
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable

// Data Types:
// BaseBundle
//   * Boolean, Int, Long, Double, String
//   * BooleanArray, IntArray, LongArray, DoubleArray, StringArray
// Bundle
//   * Byte, Char, Short, Float, CharSequence, Parcelable, Size, SizeF
//   * ParcelableArray, ParcelableArrayList, SparseParcelableArray
//   * IntegerArrayList, StringArrayList, CharSequenceArrayList
//   * Serializable
//   * ByteArray, ShortArray, CharArray, FloatArray, CharSequenceArray
//   * Bundle

// ------------------------
// Custom Data - here the user MUST provide a Saver
// ------------------------

/*
 be aware that T must be saveable by autoSaver or you must provide a custom saver!
 supported types by autoSaver (all data types that are supported by Bundle):
   - Boolean, Int, Long, Double, String
   - BooleanArray, IntArray, LongArray, DoubleArray, StringArray
   - Byte, Char, Short, Float, CharSequence, Parcelable, Size, SizeF
   - ParcelableArray, ParcelableArrayList, SparseParcelableArray
   - IntegerArrayList, StringArrayList, CharSequenceArrayList
   - Serializable
   - ByteArray, ShortArray, CharArray, FloatArray, CharSequenceArray
   - Bundle
 */


/**
 * returns a [DialogStateWithData] object holding all necessary states for the dialog
 *
 * Consider using [rememberDialogState(showing: Boolean, ...)] if you do not need any data for this dialog
 *
 * @param data the initial data of this state (should nearly always be null which means that the dialog is not visible initially)
 * @param saver the [Saver] for the holded data ([autoSaver] will be used by default which will work for all types that are supported by [Bundle])
 * @param buttonPositiveEnabled define if the positive button should be enabled initially or not
 * @param buttonNegativeEnabled define if the negative button should be enabled initially or not
 * @param dismissAllowed define if the dialog can be initially dismissed or not
 * @param swipeAllowed define if the dialog can be initially swiped away or not
 */
@Composable
fun <T : Any> rememberDialogState(
    data: T?,
    saver: Saver<MutableState<T?>, out Any> = autoSaver(),
    buttonPositiveEnabled: Boolean = true,
    buttonNegativeEnabled: Boolean = true,
    dismissAllowed: Boolean = true,
    swipeAllowed: Boolean = true
): DialogStateWithData<T> {

    // showing should survive, even screen rotations and activity recreations
    val showing = rememberSaveable { mutableStateOf(data != null) }

    // extra data - should survice screen rotations and activity recreates BUT must be reset if dialog is dismissed
    val d = rememberSaveable(saver = saver) { mutableStateOf(data) }
    if (!showing.value) {
        d.value = data
    }

    // interaction state should be reset whenever the dialog is reshown
    val interaction = remember(showing.value) {
        mutableStateOf(
            DialogInteractionSource(
                buttonPositiveEnabled = mutableStateOf(buttonPositiveEnabled),
                buttonNegativeEnabled = mutableStateOf(buttonNegativeEnabled),
                dismissAllowed = mutableStateOf(dismissAllowed),
                swipeAllowed = mutableStateOf(swipeAllowed)
            )
        )
    }
    return DialogStateWithData(showing, d, interaction)
}

// ------------------------
// Simple - NO data
// ------------------------

/**
 * returns a [DialogState] object holding all necessary states for the dialog
 *
 * Consider using [rememberDialogState] if you do need some data inside the dialog if it is shown
 *
 * @param showing the initial showing state
 * @param buttonPositiveEnabled define if the positive button should be enabled initially or not
 * @param buttonNegativeEnabled define if the negative button should be enabled initially or not
 * @param dismissAllowed define if the dialog can be initially dismissed or not
 * @param swipeAllowed define if the dialog can be initially swiped away or not
 */
@Composable
fun rememberDialogState(
    showing: Boolean = false,
    buttonPositiveEnabled: Boolean = true,
    buttonNegativeEnabled: Boolean = true,
    dismissAllowed: Boolean = true,
    swipeAllowed: Boolean = true
): DialogState {

    // showing should survive, even screen rotations and activity recreations
    val showing = rememberSaveable { mutableStateOf(showing) }

    // interaction state should be reset whenever the dialog is reshown
    val interaction = remember(showing.value) {
        mutableStateOf(
            DialogInteractionSource(
                buttonPositiveEnabled = mutableStateOf(buttonPositiveEnabled),
                buttonNegativeEnabled = mutableStateOf(buttonNegativeEnabled),
                dismissAllowed = mutableStateOf(dismissAllowed),
                swipeAllowed = mutableStateOf(swipeAllowed)
            )
        )
    }
    return DialogState(showing, interaction)
}