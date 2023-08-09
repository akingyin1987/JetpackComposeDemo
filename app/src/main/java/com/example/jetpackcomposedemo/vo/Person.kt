/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.jetpackcomposedemo.vo




/**
 *
https://blog.csdn.net/caoshen2014/article/details/123453623
需要注意，不是所有类都可以变成协变的。

只有当这个类只能生产类型 T 的值而不能消费它们时，才能变成协变的。

T 的值只能作为函数返回值时，才能变成协变的。这也是为什么协变关键词叫做 out，表明它只能作为生产者对外输出。

以下代码中，t : T 的位置叫做 in 位置，表示它是函数参数，是消费者。: T 的位置叫做 out 位置，表示它是函数返回值，是生产者。
一句话总结：生产者 out，消费者 in。Producer out Consumer in。类似 Java 的 PECS，Producer Extend Consumer Super。

out 关键字表明了所有使用 T 的地方只能是把 T 放在函数返回值 out 位置，不能放在函数参数 in 位置。因此 out 有了第二层含义，约束了 T 只在 out 位置。

 * @Description:
 * @author: aking
 * @CreateDate: 2023/2/7 10:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2023/2/7 10:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
open class Person(val name:String,val age:Int)
class Student(name: String,age: Int):Person(name, age)
class Teacher(name: String,age: Int):Person(name, age)


class  Simple<T>{
    var data : T?= null

    fun set(data:T){
        this.data = data
    }

    fun  get():T?{
        return  data
    }
}

//使用了out之后，如果想将T放入in位置会报错,不允许放在in位置; 虽然可以使用@UnsafeVariance将其放入in位置，但是运行时候会报错运行时异常

/**
 * out 关键字也就是协变，指泛型类和它的参数类型一起变化。比如 String 可以代替 Any 传参，那么 List 可以代替 List 传参。
 * @param out T
 * @property data T?
 * @constructor
 */
class Sample2<out T>(private var data:T?){


    fun  get():T?{
        return  data
    }


}

fun printContents(list: List<Any>) {
    println(list.joinToString())
}

fun printContents2(list: MutableList<Any>) {
    println(list.joinToString())
}

class Sample3<in T>(private var data:T?){


}

fun handle(sample:Simple<Person>){
    sample.set(Teacher("B",12))
}


/**
 * 协变：保留子类型
协变是指：
如果 A 是 B 的子类型，那么 Producer 是 Producer 的子类型。

Kotlin 使用 out 关键字表示泛型类在类型参数是协变的。

以下代码表示 Producer 在 T 上是协变的。
 * @param out T
 */
interface  Producer< out T>{
    fun produce():T
}

open class Animal {
    fun feed() {
    }
}

////因为 Herd 只有 get，没有允许添加或者修改操作。所以可以把它变成协变类
class Herd<out T : Animal> {
    private val _animals: List<T> = listOf()
    val size: Int
        get() = _animals.size

    operator fun get(i: Int): T = _animals[i]
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

class Cat: Animal() {
    fun cleanLitter() {
    }
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
        // feedAll 函数的参数是 Herd<Animal>，但是传入了 Herd<Cat> 所以编译器报错
        // 正常来说，喂养所有的猫是合理的，因为猫是动物的子类型。
        // 但是编译器不认为 Herd<Cat> 是 Herd<Animal> 的子类型
        //加上 out 关键字之后不报错了。因为 out 告诉编译器，Herd 是协变的，所以 Herd 是 Herd 的子类型。可以作为参数调用。
        feedAll(cats)
        //编译器不认为 Herd 是 Herd 的子类型
        //因为 Herd 只有 get，没有允许添加或者修改操作。所以可以把它变成协变类
    }
}




fun main(args: Array<String>) {
    val student = Student("A",11)
    val simple  = Sample2<Person>(student)
    val list  = listOf("123","321")
    printContents(list)

    val list1 = mutableListOf("123","321")

    //MutableList 的表现和 List 不一样，编译器没有对 List 的参数报错
   // printContents2(list1)






}

