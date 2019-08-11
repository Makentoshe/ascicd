import java.io.File
import java.io.FileNotFoundException

class ArgumentParser(private val validator: Validator) {

    fun parse(args: Array<String>): Arguments {
        val project = getProjectPath(args[0])

        val description = validator.containsDescription(project)
        if (description == null) println("[WARNING] \"description\" was not found ")

        val answer = validator.containsAnswer(project)
        if (answer == null) println("[WARNING] \"answer\" was not found ")

        val template = validator.containsTemplate(project)
        if (template == null) println("[WARNING] template was not found and will be build automatically")

        val lib = validator.containsLib(project) ?: throw FileNotFoundException("File lib was not found")
        val solution = validator.containsSolution(project) ?: throw FileNotFoundException("File solution was not found")

        return Arguments(project, lib, solution, template, answer, description)
    }

    private fun getProjectPath(string: String): File {
        try {
            return File(string)
        } catch (e: Exception) {
            throw IllegalArgumentException("$string argument is not a project path")
        }
    }
}
