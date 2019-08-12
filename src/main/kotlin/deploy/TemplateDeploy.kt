package deploy

import java.io.File

class TemplateDeploy(private val resourceDeploy: ResourceDeploy) {

    fun deployResource(destination: File) = resourceDeploy.deployResource("stepictemplate", destination)
}