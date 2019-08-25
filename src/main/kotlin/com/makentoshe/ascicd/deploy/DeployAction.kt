package com.makentoshe.ascicd.deploy

import com.makentoshe.ascicd.Action
import java.io.File
import java.util.jar.JarEntry
import java.util.jar.JarFile

class DeployAction : Action<DeployCommand> {

    override fun execute(arg: DeployCommand) {
        if (arg.target.exists()) arg.target.deleteRecursively()
        val path = arg.resource.title
        val jarFile = File(javaClass.protectionDomain.codeSource.location.path);
        if (jarFile.isFile) executeJar(jarFile, arg) else executeIde(arg.target, path)
    }

    private fun executeJar(jarFile: File, command: DeployCommand) = JarFile(jarFile).use {
        val pathToResource = command.resource.title

        val iterator = it.entries().iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (element.name.contains("$pathToResource/") && !element.isDirectory) {
                val file = element.makeFilePath(pathToResource, command.target.name).let(::File).apply { mkfiles() }
                it.getInputStream(element).transferTo(file.outputStream())
            }
        }
    }

    private fun JarEntry.makeFilePath(pathToResource: String, parentTitle: String): String {
        var filePath = name.replace("$pathToResource/", "")
        // Resources could not contains files with jar extension
        if (filePath.contains(".jar_")) filePath = filePath.replace(".jar_", ".jar")
        return parentTitle.plus("\\").plus(filePath)
    }

    private fun File.mkfiles() {
        parentFile.mkdirs()
        createNewFile()
    }

    private fun executeIde(target: File, path: String) {
        val resourceFile = File(javaClass.classLoader.getResource(path)!!.file)
        if (target.exists()) target.deleteRecursively()
        resourceFile.copyRecursively(target, true)
    }
}
