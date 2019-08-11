fun main(args: Array<String>) {
    val argumentParser = ArgumentParser(Validator())
    val templateAction = TemplateDeployAction(TemplateDeploy())
    val libAssemblerWrapper = LibShellAction("gradlew assembleDebug", templateAction)

    Main(argumentParser, libAssemblerWrapper).main(args)
}

class Main(
    private val argumentParser: ArgumentParser,
    private val action: Action
) {
    fun main(args: Array<String>) = action.execute(argumentParser.parse(args))
}
