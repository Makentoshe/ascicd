package com.makentoshe.ascicd
import com.makentoshe.ascicd.shell.Shell
import com.makentoshe.ascicd.shell.WindowsShell

val shell: Shell = WindowsShell

fun main(args: Array<String>) {
    val structure = StructureRepository().get(Unit)
    val build = Build(shell, structure)
    val make = Make(shell, structure)

    when (args[0]) {
        "build" -> build.execute(args.copyOfRange(1, args.size))
        "make" -> make.execute(args.copyOfRange(1, args.size))
        else -> println("Nothing to do")
    }
}
