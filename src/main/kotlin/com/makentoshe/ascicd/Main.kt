package com.makentoshe.ascicd

import com.makentoshe.ascicd.shell.Shell
import com.makentoshe.ascicd.shell.WindowsShell

val shell: Shell = WindowsShell

fun main(args: Array<String>) {
    val structure = StructureRepository().get(Unit)

    when (args[0]) {
        "build" -> Build(shell).execute(structure)
        else -> println("Nothing to do")
    }
}
