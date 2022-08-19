package com.example.jetpackcomposedemo.components

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.target.ImageViewTarget
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.example.jetpackcomposedemo.R
/**
 *  标准布局组件
 * @Description:
 * @author: aking
 * @CreateDate: 2022/8/17 12:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/8/17 12:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


    @Composable
    fun ArtistCard(){
        //未提供有关如何 排列这些元素，默认会重叠,类似FrameLayout
        Text(text = "Alfred Sisley", fontStyle = FontStyle.Italic, fontSize = 18.sp)
        Text("3 minutes ago")
    }

    @Composable
    fun ColumnArtistCard(){
        //Column 可将多个项垂直地放置在屏幕上 类似 LinearLayout orientation="vertical"

        Column(modifier = Modifier
            .padding(3.dp)
            .border(1.dp, Color.Black)) {
            Text(text = "Alfred Sisley", fontStyle = FontStyle.Italic, fontSize = 18.sp, modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth())
            Text("3 minutes ago", modifier = Modifier.fillMaxWidth())

        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RowArtistCard(){
        val context = LocalContext.current
        val imageUir = remember {
            mutableStateOf<Uri?>(null)
        }
        var mCameraUri: Uri? = null
        val openCameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture(), onResult = {
            if(it){
                //拍照返回结果
                imageUir.value = mCameraUri
            }
        })
        // 定义 Permission State
//        val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
//        PermissionRequired(
//            permissionState = permissionState,
//            permissionNotGrantedContent = { /*TODO*/ },
//            permissionNotAvailableContent = { /*TODO*/ }) {
//            //调用权限获取之后功能 "可以打开相机"
//        }
        //Row 可将多个项垂直地放置在屏幕上 类似 LinearLayout orientation="vertical"
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(bitmap =ImageBitmap.imageResource(id = android.R.drawable.ic_delete), contentDescription ="" )
                Column {
                    Text(text = "Alfred Sisley", fontStyle = FontStyle.Italic, fontSize = 18.sp, modifier = Modifier
                        .padding(3.dp)
                    )
                    Text("3 minutes ago")
                }
                val imgUrl="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn10100%2F278%2Fw1100h778%2F20190314%2F9384-hufnxfn0846415.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1663310814&t=9a8ee8166790db00bdd9bb55b7ef96b3"
                Image(painter = rememberAsyncImagePainter( model = ImageRequest.Builder(context).data(imageUir.value).build() , placeholder = painterResource(R.drawable.placeholder)), contentDescription = "",
                    modifier = Modifier.clickable {
//              if(permissionState.hasPermission){
//                  //当前有权限直接拍照
//                  mCameraUri = context.contentResolver.insert(if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) MediaStore.Images.Media.EXTERNAL_CONTENT_URI else MediaStore.Images.Media.INTERNAL_CONTENT_URI,
//                      ContentValues()
//                  )
//                  openCameraLauncher.launch(mCameraUri)
//              }else{
//                  permissionState.launchPermissionRequest()
//              }
                        mCameraUri = context.contentResolver.insert(if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) MediaStore.Images.Media.EXTERNAL_CONTENT_URI else MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                            ContentValues())
                        openCameraLauncher.launch(mCameraUri)

                    })


            }
            Spacer(modifier = Modifier.size(16.dp))
        }

    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun DrawerView(){
        val context = LocalContext.current

        var mCameraUri: Uri? = null

        val imageUir = remember {
            mutableStateOf<Uri?>(null)
        }
        //TakePicture 调用相机，拍照后将图片保存到开发者指定的Uri，返回true
        val openCameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { if (it) imageUir.value = mCameraUri })
        // 定义 Permission State
        val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = { /*TODO*/ },
            permissionNotAvailableContent = { /*TODO*/ }) {
            //调用权限获取之后功能 "可以打开相机"
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
            .padding(0.dp, 36.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUir.value)
                    .error(R.drawable.placeholder)
                    .crossfade(true).build(), contentDescription ="" , placeholder = painterResource(id = R.drawable.placeholder),
                modifier = Modifier
                    .size(100.dp)
                    .border(1.dp, Color.Black)
                    .clip(CircleShape)
                    .clickable {
                        if (permissionState.hasPermission) {
                            //当前有拍照权限
                            mCameraUri = context.contentResolver.insert(
                                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) MediaStore.Images.Media.EXTERNAL_CONTENT_URI else MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                                ContentValues()
                            )
                            openCameraLauncher.launch(mCameraUri)
                        } else {
                            permissionState.launchPermissionRequest()
                        }
                    },
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    @Preview()
    @Composable
    fun  ShowView(){
        JetpackComposeDemoTheme{
            RowArtistCard()
        }


    }
