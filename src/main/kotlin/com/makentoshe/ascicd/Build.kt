package com.makentoshe.ascicd

import com.makentoshe.ascicd.copy.CopyAction
import com.makentoshe.ascicd.copy.CopyCommand
import com.makentoshe.ascicd.deploy.DeployAction
import com.makentoshe.ascicd.deploy.DeployCommand
import com.makentoshe.ascicd.shell.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class Build(private val shell: Shell, private val structure: Structure) {

    fun execute() {
        val resourceRepository = ResourceRepository(structure)
        if (!structure.solution.exists() || !structure.lib.exists()) {
//            throw FileNotFoundException("Solution or Checker does not exists")
        }

//        println("Start checker assemble...")
//        ShellAction(shell).execute(ShellCommandAssemble(structure.lib) {
//            if (it.exitCode != 0) {
//                throw Exception(it.error)
//            } else {
//                println("Assemble successful")
//            }
//        })

        println("Deploy template...")
        val resource = resourceRepository.get(structure.template)
        DeployAction().execute(DeployCommand(resource, structure.template))

        return
        val solutionDestination = File(structure.solution, "checker/app-debug.aar")
        deliverCheckerTo(solutionDestination, structure)

        println("Start connected android tests for solution...")
        ShellAction(shell).execute(ShellCommand("gradlew cAT", structure.solution) {
            if (it.exitCode != 0) {
                throw Exception(it.error)
            } else {
                println("Tests run successfully")
            }
        })

        val checkerDestination = File(structure.template, "checker/app-debug.aar")
        deliverCheckerTo(checkerDestination, structure)

        println("Archive template...")
        val zipFile = File(structure.project, structure.project.nameWithoutExtension.plus(".zip"))
        val fileOutputStream = FileOutputStream(zipFile)
        ZipOutputStream(fileOutputStream).use { zipOutputStream ->
            structure.project.walkTopDown().forEach { zipOutputStream.writeZipEntry(structure.project, it) }
        }
        println("Done")
    }

    private fun deliverCheckerTo(destination: File, structure: Structure) {
        val aarFile = File(structure.lib, "app/build/outputs/aar/app-debug.aar")
        CopyAction().execute(CopyCommand(aarFile, destination))
        println("Checker ${aarFile.canonicalPath} was moved to ${destination.canonicalPath}")
    }

    private fun ZipOutputStream.writeZipEntry(source: File, file: File) {
        if (file.isDirectory) return
        val path = getZipFilePath(source, file)
        val zipEntry = ZipEntry(path)
        putNextEntry(zipEntry)
        write(file.readBytes())
        closeEntry()
    }

    private fun getZipFilePath(source: File, file: File): String {
        return file.absolutePath.replace(source.absolutePath.plus("\\"), "")
    }

    private fun clean(target: File) {
        ShellAction(shell).execute(ShellCommandClean(target) {
            if (it.exitCode != 0) println(it.error) else println("Clean successful")
        })
    }
}