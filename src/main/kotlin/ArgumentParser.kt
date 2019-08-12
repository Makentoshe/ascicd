import java.io.File

class ArgumentParser {

    fun parse(args: Array<String>): Arguments {
        val project = getProjectPath(args.firstOrNull() ?: "")
        val lib = File(project, "lib")
        val solution = File(project, "solution")
        val description = File(project, "description")
        val answer = File(project, "answer")
        val template = File(project, project.name)
        return Arguments(project, lib, solution, template, answer, description)
    }

    private fun getProjectPath(string: String): File {
        val file = File(string)
        return if (file.exists()) file else File("")
    }
}
