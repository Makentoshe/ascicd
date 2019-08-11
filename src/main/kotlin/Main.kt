import logger.KotlinLogger
import mu.KotlinLogging
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
    } catch (iae: IllegalArgumentException) {
        logger.error(iae.localizedMessage)
        return
    }

    val templateAction = TemplateDeployAction(TemplateDeploy())
    val libAssemblerWrapper = LibShellAction("gradlew assembleDebug", shell, templateAction)

    Main(argumentParser, libAssemblerWrapper).main(args)
}

class Main(
    private val argumentParser: ArgumentParser,
    private val action: Action
) {
    fun main(args: Array<String>) = action.execute(argumentParser.parse(args))
}
