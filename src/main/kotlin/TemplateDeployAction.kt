import java.io.File

class TemplateDeploy {

    fun deployTemplate(arguments: Arguments) {
        val template = File(javaClass.classLoader.getResource("template")!!.file)
        val destination = File(arguments.project, arguments.project.name)
        if (destination.exists()) destination.deleteRecursively()
        template.copyRecursively(destination, true)
    }
}

class TemplateDeployAction(
    private val templateDeploy: TemplateDeploy, private val firstAction: Action? = null
): Action {
    override fun execute(arguments: Arguments) {
        firstAction?.execute(arguments)
        // deploy template
        if (arguments.template == null) templateDeploy.deployTemplate(arguments)
    }
}