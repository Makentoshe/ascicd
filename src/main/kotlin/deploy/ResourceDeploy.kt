package deploy

import java.io.File

class ResourceDeploy {

    fun deployResource(resource: String, destination: File) {
        val resourceFile = File(javaClass.classLoader.getResource(resource)!!.file)
        if (destination.exists()) destination.deleteRecursively()
        resourceFile.copyRecursively(destination, true)
    }
}