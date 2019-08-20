package com.makentoshe.ascicd.deploy

import com.makentoshe.ascicd.Action
import java.io.File

class DeployAction : Action<DeployCommand> {

    override fun execute(arg: DeployCommand) {
        val resourceFile = File(javaClass.classLoader.getResource(arg.resource.title)!!.file)
        if (arg.target.exists()) arg.target.deleteRecursively()
        resourceFile.copyRecursively(arg.target, true)
        arg.callback.invoke(DeployResult(arg.target))
    }
}