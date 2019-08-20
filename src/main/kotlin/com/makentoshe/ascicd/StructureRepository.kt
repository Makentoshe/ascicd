package com.makentoshe.ascicd

import java.io.File

class StructureRepository: Repository<Unit, Structure> {
    override fun get(key: Unit): Structure {
        val project = File("").absoluteFile
        val lib = File(project, "lib")
        val solution = File(project, "solution")
        val description = File(project, "description")
        val answer = File(project, "answer")
        val template = File(project, project.name)
        return Structure(project, lib, solution, template, answer, description)
    }
}