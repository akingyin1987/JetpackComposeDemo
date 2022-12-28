/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo

import com.example.jetpackcomposedemo.vo.User
import com.google.gson.GsonBuilder
import org.junit.Test
import java.lang.reflect.ParameterizedType

/**
 *
 * @Description:
 * @author: aking
 * @CreateDate: 2022/12/27 20:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/12/27 20:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class TestJavaType {

    interface Call{
        fun onSuccess(data :String)
    }

    abstract class CallBack<T> : Call{
        override fun onSuccess(data: String) {

            this.javaClass.fields.forEach { field ->
                println("获取属性的类型：=${field.genericType}")
            }

            //获取超类的类型(该实体可以是类，数组，接口等。该方法返回此实体的超类的类型。)
            val genericSuperclass = this.javaClass.genericSuperclass
            if(null != genericSuperclass){
               // ParameterizedType 参数化类型，即泛型
                if(genericSuperclass is ParameterizedType){
                    genericSuperclass.actualTypeArguments.getOrNull(0)?.let {
                        val gson  = GsonBuilder().create()
                        val dataT: T  = gson.fromJson(data,it)
                        onSuccess(dataT)
                    }
                }
            }
            //获取接口定义的泛型
             this.javaClass.genericInterfaces.getOrNull(0)?.let {
                 println(it.typeName)
                val gson  = GsonBuilder().create()
                val dataT: T  = gson.fromJson(data,it)
                 onSuccess(dataT)
             }?: println("未获取到类型")
        }

        abstract fun onSuccess(data:T)
    }

    interface  IHttp{
        fun  onPost(call: Call)
    }


    class StringHttp:IHttp{
        override fun onPost(call: Call) {
            val gson  = GsonBuilder().create()
            println("StringHttp->onPost")
           call.onSuccess(gson.toJson(User()))
        }
    }

    class DataHttp(var iHttp: IHttp):IHttp{

        override fun onPost(call: Call) {
            println("DataHttp->onPost")
           iHttp.onPost(call)
        }


    }


    @Test
    fun test1(){
        DataHttp(StringHttp()).onPost(object :CallBack<User>(){
            override fun onSuccess(data: User) {
                println("user=${data}")
            }
        })
    }
}