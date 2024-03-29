/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.ksp_compiler

import com.example.ksp_annotation.Custom2Annotation
import com.example.ksp_annotation.CustomAnnotation
import com.example.ksp_annotation.CustomMeta
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
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
            val CUSTOM_CLASS_NAME = CustomAnnotation::class.qualifiedName!!

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
                val customMeta = extractCustomMeta(it)
                 val groupClassName = customMeta.tableName.ifEmpty {"Test@Group@"}

                val file = FileSpec.builder(PACKAGE_OF_GENERATE_FILE,groupClassName)
                    .addType(
                        TypeSpec.classBuilder(
                            ClassName(PACKAGE_OF_GENERATE_FILE,groupClassName)
                        ).primaryConstructor(
                            FunSpec.constructorBuilder()
                                .addParameter("name",String::class)
                                .addModifiers(KModifier.PUBLIC)
                                .addParameter(
                                    ParameterSpec.builder("userName2",String::class)
                                        .build()
                                )
                                .build()
                        ).addProperty(
                            PropertySpec.builder("userName",String::class)
                                .initializer("%S",customMeta.tableName).build()
                        )
                            .addKdoc(WARNING_TIPS)
                            .addFunction(
                                FunSpec.builder("test")
                                    .addParameter("tableName",String::class)
                                    .addStatement("println(%P)","Hello \$tableName")
                                    .build()
                            )
                            .addFunction(
                                FunSpec.builder("setUserInfo")
                                    .addParameter("userName",String::class)
                                    .addParameter("userName2",String::class)
                                   // .addStatement("this.userName=name")
                                   // .addStatement("this.userName2=userName2")
                                    .build()
                            )
                            .build()


                    ).build()

                file.writeTo(codeGenerator,true)
            }
        }


        @OptIn(KspExperimental::class)
        private fun extractCustomMeta(element:KSClassDeclaration):CustomMeta{


            val qualifiedName = element.qualifiedName?.asString()
                ?: error("local variable can not be annotated with @CustomAnnotation")

            val custom = element.getAnnotationsByType(CustomAnnotation::class).firstOrNull()?: error("$qualifiedName must annotated with @CustomAnnotation ")

            return  CustomMeta(
                tableName =  custom.tableName,
                indices = custom.indices,
                inheritSuperIndices = custom.inheritSuperIndices,
                primaryKeys = custom.primaryKeys,
                ignoredColumns = custom.ignoredColumns
            )

        }



    }
}