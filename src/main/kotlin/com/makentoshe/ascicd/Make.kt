package com.makentoshe.ascicd

import com.makentoshe.ascicd.deploy.DeployAction
import com.makentoshe.ascicd.deploy.DeployCommand
import com.makentoshe.ascicd.shell.Shell

class Make(private val shell: Shell, private val structure: Structure): Action<Array<String>> {

    override fun execute(arg: Array<String>) {
        if (arg.isEmpty()) executeAll()
        if (arg.contains("-s") || arg.contains("--solution")) executeSolution()
        if (arg.contains("-c") || arg.contains("--checker")) executeChecker()
        if (arg.contains("-t") || arg.contains("--template")) executeTemplate()
    }

    private fun executeAll() {
        executeChecker()
        executeSolution()
        executeTemplate()
    }

    private fun executeSolution() {
        println("Deploy solution...")
        val resourceRepository = ResourceRepository(structure)
        val resource = resourceRepository.get(structure.solution)
        DeployAction().execute(DeployCommand(resource, structure.solution))
    }

    private fun executeChecker() {
        println("Deploy checker...")
        val resourceRepository = ResourceRepository(structure)
        val resource = resourceRepository.get(structure.lib)
        DeployAction().execute(DeployCommand(resource, structure.lib))
    }

    private fun executeTemplate() {
        println("Deploy template...")
        val resourceRepository = ResourceRepository(structure)
        val resource = resourceRepository.get(structure.template)
        DeployAction().execute(DeployCommand(resource, structure.template))
    }
}