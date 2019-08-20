package com.makentoshe.ascicd.shell

import java.io.File

open class ShellCommand(val command: String, val target: File, val callback: (ShellResult) -> Unit = {})

open class ShellCommandAssemble(
    target: File, callback: (ShellResult) -> Unit
) : ShellCommand("gradlew assembleDebug", target, callback)

open class ShellCommandClean(
    target: File, callback: (ShellResult) -> Unit
) : ShellCommand("gradlew clean", target, callback)