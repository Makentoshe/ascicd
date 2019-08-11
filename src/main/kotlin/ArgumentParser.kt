import logger.KotlinLogger
import java.io.File
import java.io.FileNotFoundException

class ArgumentParser(private val validator: Validator, private val logger: KotlinLogger) {

    fun parse(args: Array<String>): Arguments {
        val project = getProjectPath(args.firstOrNull() ?: "")

        val lib = validator.containsLib(project) ?: throw FileNotFoundException("File lib was not found")
        val solution = validator.containsSolution(project) ?: throw FileNotFoundException("File solution was not found")

        val description = validator.containsDescription(project)
        if (description == null) logger.warn("\"description\" was not found")

        val answer = validator.containsAnswer(project)
        if (answer == null) logger.warn("\"answer\" was not found")

        val template = validator.containsTemplate(project)
        if (template == null) logger.warn("template was not found and will be build automatically")

        return Arguments(project, lib, solution, template, answer, description)
    }

    private fun getProjectPath(string: String): File {
        val file = File(string)
        return if (file.exists()) file else File("")
    }
}
