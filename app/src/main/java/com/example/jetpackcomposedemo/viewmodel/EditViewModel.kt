package com.example.jetpackcomposedemo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposedemo.vo.User

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/9 11:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/9 11:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class EditViewModel : ViewModel() {

    val  userLiveData  = MutableLiveData(User())

    var  userState by mutableStateOf(User())


    fun   onChangePassWord(passWord:String){

        userLiveData.value?.let {

            userLiveData.value = it.copy(passWord=passWord)
        }
    }

    fun  onChangeAccount(account:String){
        userState = User(account,userState.passWord)
    }
}