package com.makentoshe.ascicd.deploy

import java.io.File

data class DeployCommand(val resource: Resource, val target: File, val callback: (DeployResult) -> Unit = {})