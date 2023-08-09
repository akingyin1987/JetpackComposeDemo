/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/11/30 10:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/11/30 10:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardComposePreview(){
    JetpackComposeDemoTheme {
        Surface(Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()) {
              Card(onClick = {  }, modifier = Modifier
                  .shadow(4.dp)
                  .fillMaxWidth()
                  .padding(3.dp), shape = RoundedCornerShape(4.dp)) {
                  Text(text = "Card", modifier = Modifier.fillMaxWidth())
                  Text(text = "1234", modifier = Modifier.fillMaxWidth())
                  Text(text = "1235", modifier = Modifier.fillMaxWidth())
              }
                Card(onClick = {  }, modifier = Modifier.height(30.dp)
                    .fillMaxWidth().border(1.dp, Color.Red)
                    .padding(3.dp),
                    shape = RoundedCornerShape(4.dp),
                    elevation = CardDefaults.cardElevation()) {
                    Text(text = "Card", modifier = Modifier.fillMaxWidth())

                }

                OutlinedCard(onClick = {  }, modifier = Modifier
                    .shadow(4.dp)
                    .fillMaxWidth()
                    .padding(3.dp), shape = RoundedCornerShape(4.dp)) {
                    Text(text = "OutlinedCard", modifier = Modifier.fillMaxWidth())
                    Text(text = "1234", modifier = Modifier.fillMaxWidth())
                    Text(text = "1235", modifier = Modifier.fillMaxWidth())
                }

                ElevatedCard(onClick = {  }, modifier = Modifier
                    .shadow(4.dp)
                    .fillMaxWidth()
                    .padding(3.dp), shape = RoundedCornerShape(4.dp)) {
                    Text(text = "ElevatedCard", modifier = Modifier.fillMaxWidth())
                    Text(text = "1234", modifier = Modifier.fillMaxWidth())
                    Text(text = "1235", modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

}