package com.makentoshe.ascicd.deploy

import com.makentoshe.ascicd.Action
import java.io.File
import java.util.jar.JarFile

class DeployAction : Action<DeployCommand> {

    override fun execute(arg: DeployCommand) {
        if (arg.target.exists()) arg.target.deleteRecursively()
        val path = arg.resource.title
        val jarFile = File(javaClass.protectionDomain.codeSource.location.path);
        if (jarFile.isFile) executeJar(jarFile, path) else executeIde(arg.target, path)
    }

    private fun executeJar(jarFile: File, path: String) = JarFile(jarFile).use {
        val iterator = it.entries().iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (element.name.contains("$path/") && element.size > 0) {
                val currentFile = File("").absoluteFile
                val filePath = currentFile.name.plus("\\").plus(element.name.replace("$path/", ""))
                val file = File(currentFile, filePath).apply { mkfiles() }
                it.getInputStream(element).transferTo(file.outputStream())
            }
        }
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