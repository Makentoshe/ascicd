import deploy.CheckerDeploy

class CheckerDeployAction(
    private val checkerDeploy: CheckerDeploy,
    private val action: Action? = null,
    private val force: Boolean = false
) : Action {

    override fun execute(arguments: Arguments) {
        action?.execute(arguments)
        if (arguments.lib.exists() && !force) return
        checkerDeploy.deployResource(arguments.lib)
    }
}