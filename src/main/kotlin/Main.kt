import com.makentoshe.ascicd.Action
import com.makentoshe.ascicd.Structure
import com.makentoshe.ascicd.StructureRepository
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

val shell: Shell = WindowsShell

fun main(args: Array<String>) {
    val structure = StructureRepository().get(Unit)

    when (args[0]) {
        "build" -> Build().execute(structure)
        else -> println("Nothing to do")
    }
}

class Build : Action<Structure> {

    override fun execute(arg: Structure) {
        val resourceRepository = ResourceRepository(arg)
        if (!arg.solution.exists() || !arg.lib.exists()) {
            throw FileNotFoundException("Solution or Checker does not exists")
        }

        println("Start checker assemble...")
        ShellAction(shell).execute(ShellCommandAssemble(arg.lib) {
            if (it.exitCode != 0) println(it.error) else println("Assemble successful")
        })

        println("Deploy template...")
        val resource = resourceRepository.get(arg.template)
        DeployAction().execute(DeployCommand(resource, arg.template))

        val solutionDestination = File(arg.solution, "checker/app-debug.aar")
        deliverCheckerTo(solutionDestination, arg)

        println("Start connected android tests for solution...")
        ShellAction(shell).execute(ShellCommand("gradlew cAT", arg.solution) {
            if (it.exitCode != 0) println(it.error) else println("Tests run successfully")
        })

        val checkerDestination = File(arg.template, "checker/app-debug.aar")
        deliverCheckerTo(checkerDestination, arg)

        println("Archive template...")
        val zipFile = File(arg.project, arg.project.nameWithoutExtension.plus(".zip"))
        val fileOutputStream = FileOutputStream(zipFile)
        ZipOutputStream(fileOutputStream).use { zipOutputStream ->
            arg.project.walkTopDown().forEach { zipOutputStream.writeZipEntry(arg.project, it) }
        }

        println("Clean project...")
        clean(arg.lib)
        clean(arg.solution)
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
        println("Clean checker build...")
        ShellAction(shell).execute(ShellCommandClean(target) {
            if (it.exitCode != 0) println(it.error) else println("Clean successful")
        })
    }
}