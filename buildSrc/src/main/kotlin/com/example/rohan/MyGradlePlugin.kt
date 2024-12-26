package com.example.rohan

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.cli.common.arguments.K2JSCompilerArguments
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import org.jetbrains.kotlin.compilerRunner.toArgumentStrings


open class KotlinCompileTask: DefaultTask()
{
    @get:InputFiles
    public val kotlinSoursce = project.fileTree(project.projectDir.resolve("src")).matching({
        include("**/*.kt")
    })

    @get:OutputDirectory
    val output = project.layout.buildDirectory.dir("classes")

    @TaskAction
    fun compiler()
    {
        val ouptutDie = output.get().asFile
        ouptutDie.deleteRecursively()
        ouptutDie.mkdirs()

        val args = K2JSCompilerArguments().apply {
            noStdlib = true
            moduleName = moduleName
            outputDir = ouptutDie.absolutePath
            freeArgs = kotlinSoursce.map { it.absolutePath }
        }

        K2JVMCompiler().exec(
            System.err, MessageRenderer.GRADLE_STYLE,*args.toArgumentStrings().toTypedArray())
    }
}

class MyGradlePlugin :Plugin<Project>{
    override fun apply(target: Project) {
        target.tasks.register<KotlinCompileTask>("compileKotlin")
    }
}