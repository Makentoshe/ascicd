import deploy.CheckerDeploy
import deploy.ResourceDeploy
import deploy.TemplateDeploy
import logger.KotlinLogger
import shell.WindowsShell
import java.io.File
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val resourceDeploy = ResourceDeploy()
    val shell = WindowsShell()
    val logger = KotlinLogger()

    val argumentParser = try {
        ArgumentParser()
    } catch (fnfe: FileNotFoundException) {
        logger.error(fnfe.localizedMessage)
        return
    }
//
//    // assemble aar package
//    val libAssemblerWrapper = LibShellAction("gradlew assembleDebug", shell, null, logger)
//    // add template to repository
////    val templateAction = TemplateDeployAction(TemplateDeploy(ResourceDeploy()))
//
//    val aarDeliverAction = AarDeliverAction()
//
    val deployChecker = CheckerDeployAction(CheckerDeploy(resourceDeploy))
    val deployTemplate = TemplateDeployAction(TemplateDeploy(resourceDeploy), deployChecker)

    Main(argumentParser, deployTemplate).main(args)
}

class Main(
    private val argumentParser: ArgumentParser,
    private val action: Action
) {
    fun main(args: Array<String>) = action.execute(argumentParser.parse(args))
}

class AarDeliverAction(private val action: Action? = null) : Action {
    override fun execute(arguments: Arguments) {
        action?.execute(arguments)

        val aarFiles = ArrayList<File>()
        val checkerFolders = ArrayList<File>()

        val lib = arguments.lib!!

        lib.walkTopDown().forEach { if (it.name == "app-debug.aar") aarFiles.add(it) }

        arguments.project.walkTopDown().forEach {
            if (it.name == "checker") checkerFolders.add(it)
        }
        // TODO try to resolve multiple app-debug.aar files
        val aar = aarFiles[0] ?: return
        // deliver aar to each checker
    }
}