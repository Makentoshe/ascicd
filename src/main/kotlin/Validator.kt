import java.io.File

class Validator {

    fun containsTemplate(project: File): File? {
        return project.listFiles()?.firstOrNull { it.name == project.name && it.isDirectory }
    }

    fun containsSolution(project: File): File? {
        return project.listFiles()?.firstOrNull { it.name == "solution" && it.isDirectory }
    }

    fun containsLib(project: File): File? {
        return project.listFiles()?.firstOrNull { it.name == "lib" && it.isDirectory }
    }

    fun containsDescription(project: File): File? {
        return project.listFiles()?.firstOrNull { it.name == "description" && it.isFile }
    }

    fun containsAnswer(project: File): File? {
        return project.listFiles()?.firstOrNull { it.name == "answer" && it.isFile }
    }
}