package com.example.jetpackcomposedemo.components

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/10/8 8:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/8 8:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */



@Composable
fun DrawerCompose() {
    val scope = rememberCoroutineScope()
    val currentFlag:MutableState<DrawerItemFlag> = remember {
        mutableStateOf(DrawerItemFlag.Empty)
    }
    val drawerState = androidx.compose.material.rememberDrawerState(initialValue = androidx.compose.material.DrawerValue.Closed)
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        TopAppBar(title = { Text(text = "测试", color = MaterialTheme.colorScheme.background, fontSize = 18.sp)}, navigationIcon = {
            Icon(painter = painterResource(id = if(drawerState.isClosed) R.drawable.ic_baseline_menu_24 else R.drawable.ic_baseline_menu_open_24), tint =  MaterialTheme.colorScheme.background, contentDescription ="" , modifier = Modifier
                .fillMaxHeight()
                .width(40.dp)
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
        }, backgroundColor = MaterialTheme.colorScheme.primary)


        androidx.compose.material.ModalDrawer(drawerContent = {

            DrawerContext(currentItemFlag = currentFlag.value) {
                currentFlag.value = it
                scope.launch {
                    drawerState.close()
                }
            }
        }, drawerState =drawerState) {

        }
    }
  //  var currentItemFlag:MutableState<DrawerItemFlag> = mutableStateOf(value = DrawerItemFlag())

}



/**
 * 抽屉布局
 * @param modifier Modifier
 * @param currentItemFlag MutableState<DrawerItemFlag>
 * @param closeDrawer Function0<Unit>
 */
@Composable
private fun DrawerContext(modifier: Modifier=Modifier,currentItemFlag:DrawerItemFlag,closeDrawer:(DrawerItemFlag)->Unit){

   Column(modifier = modifier.fillMaxWidth()) {
        DrawerHeader(imageUrl = "", onPhotograph = {})
        DrawerItem(label = DrawerItemFlag.UserInfo.itemLabel, isSelected = currentItemFlag == DrawerItemFlag.UserInfo, painter = painterResource(id = DrawerItemFlag.UserInfo.iconResourceId)) {

          closeDrawer(DrawerItemFlag.UserInfo)
        }
       DrawerItem(label = DrawerItemFlag.Setting.itemLabel, isSelected = currentItemFlag == DrawerItemFlag.Setting, painter = painterResource(id = DrawerItemFlag.Setting.iconResourceId)) {

           closeDrawer(DrawerItemFlag.Setting)
       }
       DrawerItem(label = DrawerItemFlag.AppUpload.itemLabel, isSelected = currentItemFlag == DrawerItemFlag.AppUpload, painter = painterResource(id = DrawerItemFlag.AppUpload.iconResourceId)) {

           closeDrawer(DrawerItemFlag.AppUpload)
       }
   }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onPhotograph: (String) -> Unit
) {
    val context = LocalContext.current

    var mCameraUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    var  localPath  by remember {
        mutableStateOf("")
    }

    val imageUir = remember { mutableStateOf(if (imageUrl.isEmpty()) { null } else imageUrl.toUri()) }

    val dialogState = remember { mutableStateOf(false) }

    //TakePicture 调用相机，拍照后将图片保存到开发者指定的Uri，返回true
    val openCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                imageUir.value = mCameraUri
                onPhotograph.invoke(localPath)

            }

        }
    )
    // 定义 Permission State
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .height(dimensionResource(id = R.dimen.header_height))
            .padding(3.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUir.value)
                .error(R.drawable.placeholder)
                .crossfade(true).build(),
            contentDescription = "",
            error = painterResource(id = R.drawable.placeholder),
            placeholder = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .clickable {
                    if (null != imageUir.value) {
                        dialogState.value = true
                    } else {
                        onTakePicture(
                            permissionState,
                            context,
                            openCameraLauncher
                        ) { uri: Uri, s: String ->
                            mCameraUri = uri
                            localPath = s
                        }
                    }

                },
            contentScale = ContentScale.FillBounds
        )
    }
    if(dialogState.value){
        AlertDialog(onDismissRequest = { dialogState.value = false}, title = { Text(text = "提示")},
        text = { Text(text = "确定要重新拍你的头像?")}, confirmButton = { TextButton(onClick = {
                dialogState.value = false
                onTakePicture(permissionState,context,openCameraLauncher){uri: Uri, s: String ->
                    mCameraUri = uri
                    localPath = s
                    println("对话框=${mCameraUri}")
                }
            }) { Text(
                text = "拍照"
            )

            }}, dismissButton = {TextButton(onClick = { dialogState.value = false }) {
                Text(text = "取消")
            }})
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun onTakePicture(permissionState:PermissionState,context:Context,openCameraLauncher:ManagedActivityResultLauncher<Uri,Boolean>, call:(Uri, String)->Unit){
    if (permissionState.status.isGranted) {
        val localFile = File(
            context.getExternalFilesDirs(null).first(), UUID.randomUUID().toString().replace("-", "") + ".jpg"
        )
        //当前有拍照权限
       val  mCameraUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                localFile
            )
        } else {
            Uri.fromFile(localFile)
        }
       val   localPath = localFile.absolutePath
        call.invoke(mCameraUri,localPath)
        openCameraLauncher.launch(mCameraUri)

    } else {
        permissionState.launchPermissionRequest()
    }
}

/**
 * 抽屉的Item
 */
@Composable
private fun DrawerItem(modifier: Modifier=Modifier,label:String,painter: Painter,
                       tintColor:Color = MaterialTheme.colorScheme.primary,isSelected:Boolean = false,onClick:()->Unit){
    val color  = if(isSelected) tintColor else Color.Black
    Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
            .clickable { onClick() }){

        Icon(painter = painter, contentDescription = null, tint = color)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = androidx.compose.material.MaterialTheme.typography.body2, color = color)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNavigationDrawerPreview(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope  = rememberCoroutineScope()
    JetpackComposeDemoTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    if(drawerState.isOpen){
                        scope.launch {
                            drawerState.close()
                        }
                    }else{
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }) {
                     Text(text = "打开与关闭 Drawer")
                }

                ModalNavigationDrawer(drawerContent = {
                    ModalDrawerSheet(drawerShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp), drawerContainerColor = MaterialTheme.colorScheme.primary) {
                        Text(text = "ModalDrawerSheet")
                    }

                }, drawerState = drawerState) {
                    Text(text = "内容")
                }
            }

        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissibleNavigationDrawerPreview(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope  = rememberCoroutineScope()
    JetpackComposeDemoTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    if(drawerState.isOpen){
                        scope.launch {
                            drawerState.close()
                        }
                    }else{
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }) {
                    Text(text = "打开与关闭 Drawer")
                }

                DismissibleNavigationDrawer(modifier = Modifier.fillMaxSize(), drawerState = drawerState, drawerContent = {
                    DismissibleDrawerSheet {
                        Text(text = "DismissibleDrawerSheet")
                    }
                }) {
                    Text(text = "DismissibleNavigationDrawer")
                }
            }

        }
    }
}


@Preview(name = "PermanentNavigationDrawer")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermanentNavigationDrawerPreview(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope  = rememberCoroutineScope()
    JetpackComposeDemoTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    if(drawerState.isOpen){
                        scope.launch {
                            drawerState.close()
                        }
                    }else{
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }) {
                    Text(text = "打开与关闭 Drawer")
                }

                PermanentNavigationDrawer(modifier = Modifier.fillMaxSize(), drawerContent = {
                    PermanentDrawerSheet {
                        Text(text = "PermanentDrawerSheet")
                    }
                }) {
                    Text(text = "PermanentNavigationDrawer")
                }
            }

        }
    }
}
