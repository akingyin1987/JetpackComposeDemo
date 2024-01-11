/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.ksp_annotation

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/11/15 17:07
 * @version: 1.0
 */
data class CustomMeta(
    val tableName: String = "",

    val indices: IntArray = intArrayOf(),


    val inheritSuperIndices: Boolean = false,


    val primaryKeys: Array<String> = emptyArray(),



    val ignoredColumns: Array<String> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomMeta

        if (tableName != other.tableName) return false
        if (!indices.contentEquals(other.indices)) return false
        if (inheritSuperIndices != other.inheritSuperIndices) return false
        if (!primaryKeys.contentEquals(other.primaryKeys)) return false
        if (!ignoredColumns.contentEquals(other.ignoredColumns)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tableName.hashCode()
        result = 31 * result + indices.contentHashCode()
        result = 31 * result + inheritSuperIndices.hashCode()
        result = 31 * result + primaryKeys.contentHashCode()
        result = 31 * result + ignoredColumns.contentHashCode()
        return result
    }
}
