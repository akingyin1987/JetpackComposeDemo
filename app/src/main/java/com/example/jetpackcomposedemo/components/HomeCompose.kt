package com.example.jetpackcomposedemo.components

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.ModalDrawer
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposedemo.R
import kotlinx.coroutines.launch
import java.text.MessageFormat


/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/10/9 11:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/9 11:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


/**
 * 用户头像
 */

@Composable
private fun UserHeadPortrait(modifier: Modifier = Modifier,context:Context, imageUrl: String, onPhotograph: (String) -> Unit){

    val imageUir = rememberSaveable { mutableStateOf(if (imageUrl.isEmpty()) { null } else imageUrl.toUri()) }


    Box(modifier =modifier.size(100.dp), contentAlignment = Alignment.CenterEnd){
        AsyncImage(modifier = Modifier
            .size(100.dp)
            .border(
                4.dp, MaterialTheme.colorScheme.secondary,
                CircleShape
            )
            .clip(CircleShape)
            ,
            model = ImageRequest.Builder(context).data(imageUir.value)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).crossfade(true).build(), contentDescription =null,contentScale = ContentScale.FillBounds )
        Image(imageVector = Icons.Default.PhotoCamera, contentDescription =null, colorFilter = ColorFilter.tint(Color.Black) )
    }


}




@Composable
private fun HomeDrawerHeader(modifier: Modifier=Modifier, loginTime:String, projectName:String, onShareUserInfo:()->Unit, onPhotograph:(String)->Unit){
    /** 是否显示对话框 */
    val dialogState = remember { mutableStateOf(false) }
    val context = LocalContext.current



    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .height(160.dp)
        .background(MaterialTheme.colorScheme.primary)
        .padding(10.dp)) {
        //通过createRefs创建引用
        val (userHeaderImage,userName,userTel,userNickName,userShare)=createRefs()

        Image(imageVector = Icons.Default.Share, contentDescription ="分享",
            modifier = Modifier
                .constrainAs(userShare) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(5.dp)
                .clickable {

                }, colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background) )

        UserHeadPortrait(context =context ,
            modifier = Modifier.constrainAs(userHeaderImage){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(userName.top)
            },
            imageUrl = "", onPhotograph ={

                onPhotograph(it)
            } )
        Text(text = "你好", color = MaterialTheme.colorScheme.surface, modifier = Modifier.constrainAs(userName) {
            top.linkTo(userHeaderImage.bottom)
            start.linkTo(userHeaderImage.start)
            end.linkTo(userHeaderImage.end)
            bottom.linkTo(parent.bottom)
        })

        Text(text = MessageFormat.format("{0},登录 {1}项目", loginTime, projectName),
        modifier = Modifier.constrainAs(userNickName){
            absoluteLeft.linkTo(userHeaderImage.absoluteRight)
            top.linkTo(userHeaderImage.top)
            absoluteRight.linkTo(parent.absoluteRight)
            bottom.linkTo(userTel.top)
        }, color = MaterialTheme.colorScheme.surface)

        Row(modifier = Modifier.constrainAs(userTel){
            absoluteLeft.linkTo(userHeaderImage.absoluteRight)
            top.linkTo(userNickName.bottom)
            absoluteRight.linkTo(parent.absoluteRight)
            bottom.linkTo(parent.bottom)
        }, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "我的电话：{0}", modifier = Modifier
                .weight(0.9F, false)
                .clickable {
                    dialogState.value = true
                },overflow= TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.background)
            Icon(imageVector = Icons.Default.Edit, contentDescription = "编辑", tint = MaterialTheme.colorScheme.background)
        }

    }


}




@OptIn( ExperimentalMaterial3Api::class)
@Composable
private fun ShowEditMyTelDialog(oldTel:String,call:(String)->Unit){
    val newTel  = remember { mutableStateOf(oldTel) }
    Dialog(onDismissRequest = { call.invoke("") }){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 15.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "编辑电话",color = Color.Black,fontSize = 16.sp,modifier = Modifier.padding(start = 10.dp),fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "请填写你的电话",modifier = Modifier.padding(horizontal = 10.dp),lineHeight = 20.sp,fontSize = 14.sp)


            OutlinedTextField(value = newTel.value,
                onValueChange = {if(it.length<12){newTel.value = it} },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                singleLine = true, label = {
                    Text(text = "手机号码")
                }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Divider(modifier = Modifier.height(0.5.dp))
            Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
                TextButton(onClick = {  call.invoke("") }, modifier = Modifier.padding( 10.dp)) {
                    Text(text = "取消")
                }
                TextButton(onClick = {  call.invoke(newTel.value) }, modifier = Modifier.padding(10.dp)) {
                    Text(text = "确定")
                }

            }
        }
    }

}

/**
 * 抽屉的Item
 */
@Composable
private fun DrawerItem(modifier: Modifier=Modifier, label:String, painter: Painter,
                       tintColor:Color = MaterialTheme.colorScheme.primary, isSelected:Boolean = false, onClick:()->Unit){
    val color  = if(isSelected) tintColor else MaterialTheme.colorScheme.onSurface
    Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp)
            .clickable { onClick() }){

        androidx.compose.material3.Icon(painter = painter, contentDescription = null, tint = color)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = androidx.compose.material.MaterialTheme.typography.body2, color = color)

    }

}

sealed class DrawerItemFlag(var  itemLabel:String,@DrawableRes var iconResourceId:Int){
    object AppStatistics : DrawerItemFlag("统计", R.drawable.ic_baseline_cloud_upload_24)
    object MyFriends:DrawerItemFlag("我的同事",R.drawable.ic_baseline_cloud_upload_24)
    object AppUpload:DrawerItemFlag("数据上传",R.drawable.ic_baseline_cloud_upload_24)
    object AppSetting:DrawerItemFlag("设置",R.drawable.ic_baseline_cloud_upload_24)
    object Empty:DrawerItemFlag("",R.drawable.ic_baseline_cloud_upload_24)
    object UserInfo : DrawerItemFlag("用户信息", R.drawable.ic_baseline_account_box_24)
    object Setting:DrawerItemFlag("应用设置",R.drawable.ic_baseline_settings_applications_24)

}


/**
 * 抽屉布局
 * @param projectName String
 * @param modifier Modifier
 * @param currentItemFlag DrawerItemFlag
 * @param closeDrawer Function1<DrawerItemFlag, Unit>
 */
@Composable
private fun DrawerContext(projectName: String,currentAppTime:String,loginTime:String,modifier: Modifier=Modifier,currentItemFlag:DrawerItemFlag=DrawerItemFlag.Empty,closeDrawer:(DrawerItemFlag)->Unit,onShareUserInfo:()->Unit,onPhotograph:(String)->Unit){

    Column(modifier = modifier.fillMaxWidth()) {
        HomeDrawerHeader( projectName = projectName,  loginTime = loginTime, onShareUserInfo = onShareUserInfo, onPhotograph = onPhotograph)
        DrawerItem(label = DrawerItemFlag.AppStatistics.itemLabel, isSelected = currentItemFlag == DrawerItemFlag.AppStatistics, painter = painterResource(id = DrawerItemFlag.AppStatistics.iconResourceId)) {

            closeDrawer(DrawerItemFlag.AppStatistics)
        }
        DrawerItem(label = DrawerItemFlag.MyFriends.itemLabel, isSelected = currentItemFlag == DrawerItemFlag.MyFriends, painter = painterResource(id = DrawerItemFlag.MyFriends.iconResourceId)) {

            closeDrawer(DrawerItemFlag.MyFriends)
        }
        DrawerItem(label = DrawerItemFlag.AppUpload.itemLabel, isSelected = currentItemFlag == DrawerItemFlag.AppUpload, painter = painterResource(id = DrawerItemFlag.AppUpload.iconResourceId)) {

            closeDrawer(DrawerItemFlag.AppUpload)
        }
        DrawerItem(label = DrawerItemFlag.AppSetting.itemLabel, isSelected = currentItemFlag == DrawerItemFlag.AppSetting, painter = painterResource(id = DrawerItemFlag.AppSetting.iconResourceId)) {

            closeDrawer(DrawerItemFlag.AppSetting)
        }
    }
}

sealed class HomeEvent{
    object  GoToUploadActivity:HomeEvent()
    object  OnScanEvent:HomeEvent()
    object  GoToStatisticsActivity:HomeEvent()
    object  OnShareEvent:HomeEvent()
    object  OnAppSettingEvent:HomeEvent()
    object  OnMyFriend:HomeEvent()

}


@Composable
private fun  HomeHeader(totalJob:Int,progress:Int,onHomeEvent:(HomeEvent)->Unit){

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)
        .border(1.dp, MaterialTheme.colorScheme.secondary)
        .background(MaterialTheme.colorScheme.primary), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {

        Button(onClick = {

        },
            modifier = Modifier
                .padding(start = 20.dp)
                ,elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 3.dp), colors =ButtonDefaults.buttonColors(containerColor=MaterialTheme.colorScheme.surfaceTint) , shape = RoundedCornerShape(3.dp)) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "扫二维码", color = MaterialTheme.colorScheme.background)
                Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription =null, tint = MaterialTheme.colorScheme.background,modifier = Modifier.padding(top=5.dp) )
            }
        }
        ArcProgress(progress = progress, total = totalJob,
            modifier = Modifier) {
            onHomeEvent.invoke(HomeEvent.GoToStatisticsActivity)
        }
        Button(onClick = { onHomeEvent.invoke(HomeEvent.GoToUploadActivity) },
            modifier = Modifier
                .padding(end = 20.dp), elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 3.dp), colors =ButtonDefaults.buttonColors(containerColor=MaterialTheme.colorScheme.surfaceTint) , shape = RoundedCornerShape(3.dp)) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "上传数据", color = MaterialTheme.colorScheme.background)
                Icon(imageVector = Icons.Default.CloudUpload, contentDescription =null, modifier = Modifier.padding(top=5.dp), tint = MaterialTheme.colorScheme.background )
            }
        }
    }
//   ConstraintLayout(modifier = Modifier
//       .padding(top = 1.dp)
//       .fillMaxWidth()
//       .height(130.dp)
//       .background(MaterialTheme.colorScheme.primary)) {
//
//       val (btnScan,btnUpload,btnStatistics)=createRefs()
//
//   }
}

@Composable
 fun ArcProgress(modifier: Modifier=Modifier,progress: Int,total:Int,onClick: () -> Unit){
    val animateAngle = (progress.toFloat()/total) *360
    val primary = MaterialTheme.colorScheme.onPrimaryContainer
    Box(modifier = modifier
        .width(105.dp)
        .height(105.dp)
        .clickable { onClick() }, contentAlignment = Alignment.Center ){
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp)){

            drawArc(startAngle = 180F+animateAngle, useCenter = false, sweepAngle = 360F-animateAngle,color = Color.White, style = Stroke(width = 10F, cap = StrokeCap.Round, miter = 1f))
            if(animateAngle>0){
                drawArc(startAngle = 180F, sweepAngle = animateAngle, useCenter = false, color = primary, style = Stroke(width = 10F, cap = StrokeCap.Round, miter = 1f)) }

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "任务进度", color = MaterialTheme.colorScheme.background, fontSize = 18.sp)
            Text(text = "$progress/$total", color = MaterialTheme.colorScheme.background, fontSize = 12.sp)
        }

    }
}

data class FunVo(var title:String,var icon:Int=R.drawable.ic_baseline_cloud_upload_24,var number:Int)


@Composable
fun FunctionItem(funVo:FunVo,modifier: Modifier,onClick: (FunVo) -> Unit){
    Card(
        modifier = modifier
            .height(120.dp)
            .fillMaxWidth()
            .clickable {
                onClick.invoke(funVo)
            }
    , shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout(modifier = Modifier

            .fillMaxWidth()
            .fillMaxHeight()
            .padding(3.dp)
        ) {
            val (textTitle,appIcon,badge) = createRefs()
            createVerticalChain(textTitle,appIcon, chainStyle = ChainStyle.Packed)

            Text(text = funVo.title,
                textAlign = TextAlign.Center, fontSize = 18.sp,
                modifier = Modifier
                    .constrainAs(textTitle) {
                        top.linkTo(parent.top, 5.dp)
                        bottom.linkTo(appIcon.top)
                    }
                    .fillMaxWidth())
            Image(painter = painterResource(
                id = funVo.icon
            ), contentDescription = "", modifier = Modifier
                .fillMaxWidth()
                .constrainAs(appIcon) {
                    top.linkTo(textTitle.bottom, 5.dp)
                    bottom.linkTo(parent.bottom)
                }, colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))
            if(funVo.number>0){
                Badge(modifier = Modifier
                    .padding(3.dp)
                    .constrainAs(badge) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(5.dp), contentColor = Color.Red){
                    Text(text = funVo.number.toString(), color = MaterialTheme.colorScheme.background)
                }
            }

        }
    }

}





@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode =UI_MODE_NIGHT_YES , apiLevel = 33, showBackground = true)
@Composable
fun HomeScreenCompose() {
    val scope = rememberCoroutineScope()

    val state = rememberLazyGridState()

    val drawerState = rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)

    val currentFlag:MutableState<DrawerItemFlag> = remember { mutableStateOf(DrawerItemFlag.Empty) }

    val  listData by remember {
        mutableStateOf(List(10) {
            FunVo(title = "测试${it}", number = it)
        })
    }


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        SmallTopAppBar(modifier = Modifier.shadow(2.dp), title = {
            Text(text = "抄表", style = MaterialTheme.typography.titleSmall)
        }, navigationIcon = {
            androidx.compose.material3.Icon(imageVector = if(drawerState.isOpen)Icons.Default.MenuOpen else Icons.Default.Menu, contentDescription ="" , modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
                .clickable {
                    scope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {


                            drawerState.open()
                        }
                    }
                })
        }, actions = {
            androidx.compose.material3.Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.clickable {

              })
            })



        ModalNavigationDrawer(drawerContent = {


            DrawerContext(
               projectName ="项目名称" ,
               currentAppTime ="登录时间1" ,
               loginTime = "登录时间2",
               closeDrawer ={
                    currentFlag.value = it
                    scope.launch {
                        drawerState.close()
                    }

               } ,
               onShareUserInfo = {  },
               onPhotograph = {}
           )

        },  gesturesEnabled = false, drawerState =drawerState) {
           Column(modifier = Modifier
               .fillMaxWidth()
               .fillMaxHeight()) {
               HomeHeader(100,90){}



               LazyVerticalGrid(modifier = Modifier
                 , columns = GridCells.Fixed(3), state = state,
                   horizontalArrangement = Arrangement.spacedBy(5.dp),
                   verticalArrangement = Arrangement.spacedBy(5.dp)
                     ){
                   items(listData.size, key = {item -> item }){ item ->

                       FunctionItem(listData[item], modifier = Modifier){funVo->

                       }

                   }

               }

           }
        }
    }
}

