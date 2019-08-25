package com.makentoshe.ascicd

import com.makentoshe.ascicd.deploy.CheckerResource
import com.makentoshe.ascicd.deploy.Resource
import com.makentoshe.ascicd.deploy.SolutionResource
import com.makentoshe.ascicd.deploy.TemplateResource
import java.io.File

class ResourceRepository(private val structure: Structure) : Repository<File, Resource> {
    override fun get(key: File) = when (key) {
        structure.lib -> CheckerResource
        structure.template -> TemplateResource
        structure.solution -> SolutionResource
        else -> throw Exception("Unknown target $key")
    }
}