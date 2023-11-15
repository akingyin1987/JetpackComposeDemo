/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.ksp_compiler

import com.example.ksp_annotation.Custom2Annotation
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo


/**
 *
 * @author: aking <a href="mailto:akingyin@163.com">Contact me.</a>
 * @since: 2023/11/15 14:58
 * @version: 1.0
 */
class CustomSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
       return CustomSymbolProcessor(KSPLoggerWrapper(environment.logger),environment.codeGenerator,environment.options)
    }


    class CustomSymbolProcessor(
        //日志
        private val logger: KSPLoggerWrapper,
        // 代码生成器
        private val codeGenerator: CodeGenerator,
        //ksp 配置的参数
        private val options:Map<String,String>
    ):SymbolProcessor{
        companion object {
            val CUSTOM_CLASS_NAME = Custom2Annotation::class.qualifiedName!!

        }

        //生成的model name
        private val moduleName = options.findModuleName(logger)

        //生成的文档
        private val generateDoc =VALUE_ENABLE == options[KEY_GENERATE_DOC]

        override fun process(resolver: Resolver): List<KSAnnotated> {

            logger.warn("process-->>>>>>>${CUSTOM_CLASS_NAME}")
            logger.warn("option->>>>>${options}")
            val symbol = resolver.getSymbolsWithAnnotation(CUSTOM_CLASS_NAME)
            logger.warn("size=${symbol.count()}")


            val elements = symbol
                .filterIsInstance<KSClassDeclaration>()
                .toList()


            logger.warn("process-->>>>>>>${elements.size}")
            if (elements.isNotEmpty()) {
                logger.warn(">>> AutowiredSymbolProcessor init. <<<")
                try {

                    parseCustom(elements)
                } catch (e: Exception) {
                    logger.exception(e)
                }
            }
            return emptyList()
        }


        private fun  parseCustom(elements: List<KSClassDeclaration>){
            elements.forEach {
                 val groupClassName = "Test@Group@"

                val file = FileSpec.builder(PACKAGE_OF_GENERATE_FILE,groupClassName)
                    .addType(
                        TypeSpec.classBuilder(
                            ClassName(PACKAGE_OF_GENERATE_FILE,groupClassName)
                        ).addKdoc(WARNING_TIPS)
                            .addFunction(FunSpec.builder("test").build())
                            .build()


                    ).build()

                file.writeTo(codeGenerator,true)
            }
        }




    }
}