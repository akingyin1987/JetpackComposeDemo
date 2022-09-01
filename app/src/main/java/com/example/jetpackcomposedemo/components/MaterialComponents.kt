package com.example.jetpackcomposedemo.components

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



/**
 *Material 组件和布局
 * @Description:
 * @author: aking
 * @CreateDate: 2022/8/18 16:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/8/18 16:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
val colors = listOf(
    Color(0xFFffd7d7.toInt()),
    Color(0xFFffe9d6.toInt()),
    Color(0xFFfffbd0.toInt()),
    Color(0xFFe3ffd9.toInt()),
    Color(0xFFd0fff8.toInt())
)

@Composable
fun MyApp(){

    Column(modifier = Modifier.padding(3.dp)) {
        val clickNumber = remember {
            mutableStateOf(0)
        }
        Button(onClick = {
            clickNumber.value = clickNumber.value+1
        },
            //按钮正视图高度，类似阴影这种效果，体现层次感
            elevation=ButtonDefaults.buttonElevation(0.dp) ,
            contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp),
            //圆角
            shape = RoundedCornerShape(2.dp),

        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            //空位占格符
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Like ${clickNumber.value}")
        }
    }
}


/**
 * ExtendedFloatingActionButton 有两个槽，分别针对 icon 和 text 标签，
 * 而没有通用 content lambda。虽然每个槽都支持通用的可组合内容，
 * 但该组件会自行判断这些内部内容的布局方式。
 * 它会在内部处理内边距、对齐方式和大小。
 * @param click Function0<Unit>
 */
@Composable
fun ExtendedFloatingActionButtonCompose(click:()->Unit){
    ExtendedFloatingActionButton(text = {
        Text(text = "Like ")
    }, icon = {
        Icon(
            Icons.Filled.Face,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
    }, onClick = { click.invoke() })

}

sealed class BottomItemScreen(val route: String, val title: String, val icon: ImageVector){
    object HOME: BottomItemScreen("home","首页", Icons.Default.Home)
    object Item1: BottomItemScreen("item1","收藏", Icons.Default.Favorite)
    object Item2: BottomItemScreen("item2","Item2", Icons.Default.Facebook)
    object Item3: BottomItemScreen("item3","Item3", Icons.Sharp.AddShoppingCart)
}


/**
 * 提供了适用于各种组件和其他屏幕元素的槽
 * 有一个通用 content 尾随 lambda 槽。
 * lambda 会收到应该应用于内容根目录（例如，通过 Modifier.padding）的 PaddingValues 实例，
 * 以便偏移顶部栏和底部栏（如果存在的话）。
 */



@Composable
fun ScaffoldCompose(){
    val context = LocalContext.current
    val currentRoute = remember {
        mutableStateOf(BottomItemScreen.HOME.route)
    }
   androidx.compose.material.Scaffold(topBar = {
       TopAppBar(title = { Text(text = "主页面", color = Color.White, modifier = Modifier
           .fillMaxWidth()
           .wrapContentSize(Alignment.CenterStart))},
           modifier = Modifier.fillMaxWidth(),
           navigationIcon = {
              IconButton(onClick = {
                  Toast.makeText(context,"返回",Toast.LENGTH_SHORT).show()
              }) {
                  Icon(imageVector = Icons.Filled.Menu, contentDescription = "",tint = Color.White)
              }
           },
           actions={
           IconButton(onClick = { }) {
               Icon(imageVector = Icons.Filled.Share, contentDescription = "")
           }
           IconButton(onClick = {

           }) {
               Icon(imageVector = Icons.Filled.MenuOpen, tint = Color.White, contentDescription = "")
           }

               IconButton(onClick = {

               }) {
                   Icon(imageVector = Icons.Filled.MenuBook, contentDescription = "")
               }
               IconButton(onClick = {

               }) {
                   Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "", tint = Color.White)
               }
           })
   }, bottomBar = {
       BottomNavigation(){
           val navItem = listOf(BottomItemScreen.HOME,BottomItemScreen.Item1,BottomItemScreen.Item2,BottomItemScreen.Item3)

           navItem.forEach {
               println("itemSelect=${it.route == currentRoute.value}")
               //设置item 标签
               BottomNavigationItem(modifier = Modifier.fillMaxHeight(), label = { Text(text = it.title, textAlign = TextAlign.Center)},
                   //设置图标
               icon = { Icon(imageVector = it.icon, contentDescription = "")},
               alwaysShowLabel= false,
//               selectedContentColor = Color.Green,
//               unselectedContentColor = Color.Black,

                   selected = currentRoute.value == it.route,//选中时赋值,
                   onClick = {
                       //点击时根据，选中了不同items,则先赋值，在进行路线导航，导航后保存状态，
                       currentRoute.value = it.route
                   })
           }
       }
   }, floatingActionButton = {
       androidx.compose.material.FloatingActionButton(onClick = {  }, shape = RoundedCornerShape(50.dp), backgroundColor =  androidx.compose.material.MaterialTheme.colors.secondary) {
           IconButton(onClick = {}) {
               Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite")
           }
       }
   }, isFloatingActionButtonDocked = true,
   floatingActionButtonPosition = FabPosition.Center) { contentPadding->
      
       val scrollState = rememberScrollState()
       // Column is a composable that places its children in a vertical sequence. You
       // can think of it similar to a LinearLayout with the vertical orientation.
       Column(
           modifier = Modifier.fillMaxHeight()
             .verticalScroll(scrollState, reverseScrolling = false)
               .padding(contentPadding)
              // .scrollable(scrollState, orientation = Orientation.Vertical)
       ) {
           Text(text = currentRoute.value)
           repeat(100) {
               // Card composable is a predefined composable that is meant to represent
               // the card surface as specified by the Material Design specification. We
               // also configure it to have rounded corners and apply a modifier.
               androidx.compose.material.Card(
                   backgroundColor = colors[it % colors.size],
                   shape = RoundedCornerShape(8.dp),
                   modifier = Modifier.padding(8.dp)
               ) {
                   Spacer(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(200.dp)
                   )
               }
           }
       }

   }

}


@Preview
@Composable
fun DefaultPreview1() {
    ScaffoldCompose()
}
