package deploy

import java.io.File

class CheckerDeploy(private val resourceDeploy: ResourceDeploy) {

    fun deployResource(destination: File) = resourceDeploy.deployResource("checkertemplate", destination)
}