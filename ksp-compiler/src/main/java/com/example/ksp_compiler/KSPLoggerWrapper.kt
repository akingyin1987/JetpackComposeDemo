/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.ksp_compiler

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode

/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/11/15 15:00
 * @version: 1.0
 */
class KSPLoggerWrapper(private val logger: KSPLogger):KSPLogger {

    companion object {
        const val TAG =  "Custom::Compiler "
    }

    override fun error(message: String, symbol: KSNode?) {
        if (message.isNotEmpty()) {
            logger.error("$TAG${message}", symbol)
        }
    }

    override fun exception(e: Throwable) {
        logger.exception(e)
    }

    /**
     * When dev, please use logger.warn to print logo at terminal
     * more detail can see this issue
     * (No messages displayed when I use KSPLogger.logging and info.)[https://github.com/google/ksp/issues/1111]
     * */
    override fun info(message: String, symbol: KSNode?) {
        if (message.isNotEmpty()) {
            logger.info("$TAG${message}", symbol)
        }
    }

    override fun logging(message: String, symbol: KSNode?) {
        if (message.isNotEmpty()) {
            logger.logging("$TAG${message}", symbol)
        }
    }

    override fun warn(message: String, symbol: KSNode?) {
        if (message.isNotEmpty()) {
            logger.warn("$TAG${message}", symbol)
        }
    }
}