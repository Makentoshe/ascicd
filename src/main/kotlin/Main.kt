import shell.WindowsShell

fun main(args: Array<String>) {
    val argumentParser = ArgumentParser(Validator())
    val templateAction = TemplateDeployAction(TemplateDeploy())
    val shell = WindowsShell()
    val libAssemblerWrapper = LibShellAction("gradlew assembleDebug", shell, templateAction)

    Main(argumentParser, libAssemblerWrapper).main(args)
}

class Main(
    private val argumentParser: ArgumentParser,
    private val action: Action
) {
    fun main(args: Array<String>) = action.execute(argumentParser.parse(args))
}
