package com.makentoshe.ascicd.shell

import com.makentoshe.ascicd.Action
import java.io.BufferedReader
import java.io.InputStreamReader

class ShellAction(private val shell: Shell) : Action<ShellCommand> {

    override fun execute(arg: ShellCommand) {
        val executionCommand = shell.shell.plus(arg.command)
        Runtime.getRuntime().exec(executionCommand, null, arg.target).let(::process).also(arg.callback::invoke)
    }

    private fun process(process: Process): ShellResult {
        // close unused output stream
        process.outputStream.close()
        // get default output
        val output = BufferedReader(InputStreamReader(process.inputStream)).readText()
        // get error output
        val error = BufferedReader(InputStreamReader(process.errorStream)).readText()
        // wait execution and receive an exit code
        val code = process.waitFor()
        return ShellResult(code, output, error)
    }
}