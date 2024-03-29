/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.ksp_compiler

import com.google.devtools.ksp.processing.KSPLogger

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/11/15 15:23
 * @version: 1.0
 */


const val KEY_MODULE_NAME ="key_module_name"

const val KEY_GENERATE_DOC = "KEY_GENERATE_DOC"

const val VALUE_ENABLE = "enable"

const val PACKAGE_OF_GENERATE_FILE = "com.ksp.test";

const val WARNING_TIPS = "DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER."


/**
 *  Find module name from ksp arguments, please add this config
 *  " ksp { arg("AROUTER_MODULE_NAME", project.getName()) } "
 *  in your module's build.gradle
 * */
@Suppress("SpellCheckingInspection")
internal fun Map<String, String>.findModuleName(logger: KSPLogger): String {
    val name = this[KEY_MODULE_NAME]
    return if (!name.isNullOrEmpty()) {
        @Suppress("RegExpSimplifiable", "KotlinConstantConditions")
        name.replace("[^0-9a-zA-Z_]+".toRegex(), "")
    } else {
        logger.error("These no module name, at 'build.gradle', like :\\n\" +\n" +
                "            \"ksp {\\n\" +\n" +
                "            \"    arg(\\\"AROUTER_MODULE_NAME\\\", project.getName()) {\\n\" +\n" +
                "            \"}\\n")
        throw RuntimeException("ARouter::Compiler >>> No module name, for more information, look at gradle log.")
    }
}