package com.example.jetpackcomposedemo.vo

import androidx.compose.runtime.Stable

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/9/9 10:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/9/9 10:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

data class User(var name:String="", val passWord:String="",var version:Int =0){

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + passWord.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (passWord != other.passWord) return false

        return true
    }

    override fun toString(): String {
        return "User(name='$name', passWord='$passWord', version=$version)"
    }


}
