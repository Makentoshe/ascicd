import deploy.TemplateDeploy

class TemplateDeployAction(
    private val templateDeploy: TemplateDeploy,
    private val firstAction: Action? = null,
    private val force: Boolean = false
) : Action {
    override fun execute(arguments: Arguments) {
        firstAction?.execute(arguments)
        // deploy template
        if (!arguments.template.exists() || force) {
            templateDeploy.deployResource(arguments.template)
        }
    }
}