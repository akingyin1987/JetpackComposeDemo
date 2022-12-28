/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo

import com.example.jetpackcomposedemo.vo.User
import org.junit.Test

/**
 * 运算符重载就是对已有的运算符赋予他们新的含义。重载的修饰符是operator。

比如我们的+号，它的含义是两个数值相加：1+1=2。+号对应的函数名是plus

比如我们定义一个类，如何才能使用 + 这个符号呢？其实也很简单，使用 operator 重载 plus 函数就能搞定，我们试试吧
 * @Description:
 * @author: aking
 * @CreateDate: 2022/12/28 9:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/12/28 9:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class UseCaseTest {


    operator fun invoke(message:String):String{
          return message.plus("[123]")
    }

    operator  fun  plus(user: User):User{
        user.version = user.version+1
        return user
    }

    @Test
    fun test(){
        val useCaseTest = UseCaseTest()
        println("message=${useCaseTest("123")}")
    }

    @Test
    fun test1(){
        val useCaseTest = UseCaseTest()
      val user  =  useCaseTest + User()
        println("user=${user}")
    }
}