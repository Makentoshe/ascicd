import shell.Shell
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Contains a command execution result.
 */
data class ExecutionResult(val exitCode: Int, val output: String, val error: String)

/**
 * Executes a shell command in Windows
 */
abstract class ShellAction(
    private val command: String, private val shell: Shell, private val firstAction: Action? = null
) : Action {

    open fun getWorkingDir(arguments: Arguments): File? = null

    open fun getEnvironmentVariables(arguments: Arguments): Array<String>? = null

    override fun execute(arguments: Arguments) {
        firstAction?.execute(arguments)
        val executionCommand = shell.shell.plus(command)
        val environmentVariables = getEnvironmentVariables(arguments)
        val workingDirectory = getWorkingDir(arguments)
        Runtime.getRuntime().exec(executionCommand, environmentVariables, workingDirectory).let(::process)
    }

    protected open fun process(process: Process): ExecutionResult {
        // close unused output stream
        process.outputStream.close()
        // get default output
        val output = BufferedReader(InputStreamReader(process.inputStream)).readText()
        // get error output
        val error = BufferedReader(InputStreamReader(process.errorStream)).readText()
        // wait execution and receive an exit code
        val code = process.waitFor()
        return ExecutionResult(code, output, error)
    }
}

open class LibShellAction(command: String, shell: Shell, action: Action? = null) : ShellAction(command, shell, action) {
    override fun getWorkingDir(arguments: Arguments) = arguments.lib

    override fun process(process: Process): ExecutionResult {
        return super.process(process).apply {
            println(output)
        }
    }
}
