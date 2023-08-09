/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components.banner
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.abs
/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2023/2/3 18:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/2/3 18:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
sealed class Direction {
    object Vertical : Direction()
    object Horizontal : Direction()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    count: Int,
    loop: Boolean = false,
    direction: Direction = Direction.Horizontal,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    itemSpacing: Dp = 0.dp,
    reverseLayout: Boolean = false,
    state: BannerState = rememberBannerState(),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable() (BannerScope.(page: Int) -> Unit),
) {

    val realCount = if (loop) Int.MAX_VALUE else count
    val startIndex = if (loop) Int.MAX_VALUE / 2 else state.initialPage

    val pagerState = rememberPagerState(initialPage = startIndex)

    val bannerScope = remember(state, startIndex, count) {
        state.pageState = pagerState
        state.showPageCount = count
        state.initialPage = startIndex
        BannerScopeImpl(state)
    }

    LaunchedEffect(loop) {
        pagerState.scrollToPage(startIndex)
    }
    when (direction) {
        is Direction.Horizontal -> {
            HorizontalPager(
                count = realCount,
                state = pagerState,
                contentPadding = contentPadding,
                itemSpacing = itemSpacing,
                reverseLayout = reverseLayout,
                modifier = modifier,
                verticalAlignment = verticalAlignment
            ) { index ->
                val page = if (loop) (index - startIndex).floorMod(count) else index
                bannerScope.content(page)
            }
        }

        is Direction.Vertical -> {
            VerticalPager(
                count = realCount,
                state = pagerState,
                contentPadding = contentPadding,
                itemSpacing = itemSpacing,
                reverseLayout = reverseLayout,
                modifier = modifier,
                horizontalAlignment = horizontalAlignment
            ) { index ->
                val page = if (loop) (index - startIndex).floorMod(count) else index
                bannerScope.content(page)
            }
        }
    }

}

@Stable
interface BannerScope {
    val currentPage: Int
    val currentPageOffset: Float
    val initialPage: Int
    val showPageCount: Int
}

@ExperimentalPagerApi
private class BannerScopeImpl(
    private val state: BannerState,
) : BannerScope {
    override val currentPage: Int get() = state.currentPage
    override val currentPageOffset: Float get() = state.currentPageOffset
    override val initialPage: Int
        get() = state.initialPage
    override val showPageCount: Int
        get() = state.showPageCount
}

@Composable
fun rememberBannerState(
    @IntRange(from = 0) initialPage: Int = 0,
): BannerState = rememberSaveable(saver = BannerState.Saver) {
    BannerState(
        initialPage = initialPage,
    )
}

class BannerState(
    @IntRange(from = 0) var initialPage: Int = 0,
) {

    @OptIn(ExperimentalPagerApi::class)
    internal lateinit var pageState: PagerState

    private var _pageCount: Int by mutableStateOf(0)

    @OptIn(ExperimentalPagerApi::class)
    val realPageCount: Int
        get() = pageState.pageCount

    @OptIn(ExperimentalPagerApi::class)
    val currentPage: Int
        get() = pageState.currentPage

    @OptIn(ExperimentalPagerApi::class)
    val currentPageOffset: Float
        get() = pageState.currentPageOffset

    @get:IntRange(from = 0)
    var showPageCount: Int
        get() = _pageCount
        internal set(value) {
            if (value != _pageCount) {
                _pageCount = value
            }
        }

    @OptIn(ExperimentalPagerApi::class)
    suspend fun scrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
    ) {
        pageState.scrollToPage(page, pageOffset)
    }

    @OptIn(ExperimentalPagerApi::class)
    suspend fun animateScrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
    ) {
        pageState.animateScrollToPage(page, pageOffset)
    }

    companion object {
        val Saver: Saver<BannerState, *> = Saver(
            save = {
                it.initialPage
            },
            restore = {
                BannerState(
                    initialPage = it as Int,
                )
            }
        )
    }
}

fun BannerScope.calculateCurrentOffsetForPage(page: Int): Float {
    return abs(currentPage - initialPage - page) % showPageCount + currentPageOffset
}

fun Int.floorMod(other: Int): Int = when (other) {

    0 -> this
    else -> this - floorDiv(other) * other
}


@Preview
@Composable
fun BannerPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        val dataList = remember {
            mutableStateOf(listOf("123", "1111"))
        }
        if (dataList.value.isNotEmpty()) {
            Banner(count = dataList.value.size, modifier = Modifier.fillMaxWidth()) {
                Text(text = dataList.value[it])
            }
        }
        Button(onClick = {
            if (dataList.value.size % 3 == 0) {
                dataList.value = emptyList()
            } else {
                dataList.value = listOf("1", "2", "3")
            }
        }) {
            Text(text = "改变数据")
        }

    }
}
