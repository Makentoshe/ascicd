import logger.KotlinLogger
import shell.WindowsShell
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val shell = WindowsShell()
    val logger = KotlinLogger()

    val argumentParser = try {
        ArgumentParser(Validator(), logger)
    } catch (fnfe: FileNotFoundException) {
        logger.error(fnfe.localizedMessage)
        return
    }

    val templateAction = TemplateDeployAction(TemplateDeploy(logger))
//    val libAssemblerWrapper = LibShellAction("gradlew assembleDebug", shell, templateAction, logger)

    Main(argumentParser, templateAction).main(args)
}

class Main(
    private val argumentParser: ArgumentParser,
    private val action: Action
) {
    fun main(args: Array<String>) = action.execute(argumentParser.parse(args))
}
