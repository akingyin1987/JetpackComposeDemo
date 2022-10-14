package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ModalDrawer
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/10/12 8:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/10/12 8:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Preview
@Composable
fun TopBarComposePreview(){
    JetpackComposeDemoTheme {
        Surface{
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                TopAppBar(elevation = 4.dp,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    navigationIcon = { Icon(imageVector = Icons.Default.ArrowBack, contentDescription =null )},
                    title = { Text(text = "测试")})
                ModalDrawer(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), content = {
                    Column(modifier = Modifier.height(130.dp).fillMaxWidth().background(MaterialTheme.colorScheme.primary)) {
                        
                    }
                } , drawerContent = {}) 
            }
        }
    }
}