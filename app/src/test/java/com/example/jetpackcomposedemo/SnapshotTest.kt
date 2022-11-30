/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot
import com.example.jetpackcomposedemo.vo.User

import org.junit.Test

/**
 *  快照测试
 * @Description:
 * @author: aking
 * @CreateDate: 2022/11/1 11:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/11/1 11:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class SnapshotTest {

    @Test
    fun test1(){
        //创建状态(主线开发)
        val state  = mutableStateOf(1)

        val  user = User(name = "test", passWord = "123", version = 11)

        //创建快照（开分支）
        val snapshot  = Snapshot.takeSnapshot()

        //修改状态（主线状态）
        state.value = 2

        user.name ="12333333"

        println("state=${state.value}")
        println("user=${user.toString()}")

        snapshot.enter {
            //进入快照 只能看到快照被创建时刻的最新状态
            println("snapshot state=${state.value}")
            println("user1=${user.toString()}")
            //快照内只可读不可修改
            //state.value = 3
        }
        //读取状态
        println("state1=${state.value}")

        //废弃快照
        snapshot.dispose()
    }


    @Test
    fun test2(){
        //创建状态(主线开发)
        val state  = mutableStateOf(1)



        //创建快照 可写（开分支）
        val snapshot  = Snapshot.takeMutableSnapshot()

        //修改状态（主线状态）
        state.value = 2


        println("state=${state.value}")


        snapshot.enter {
            //进入快照 只能看到快照被创建时刻的最新状态
            println("snapshot state1=${state.value}")
            //修改当前快照状态，只有快照内能可见
            state.value = 3
            println("snapshot state2=${state.value}")


        }
        println("state1=${state.value}")
        //提交snapshot 中的状态修改，
        println("result=${snapshot.apply().succeeded}")
        //读取状态
        println("state2=${state.value}")

        //废弃快照
        snapshot.dispose()
    }

    @Test
    fun test3(){
        //创建状态(主线开发)
        val state  = mutableStateOf(1)
        Snapshot.withMutableSnapshot {

        }
    }
}