package logger

interface Logger {

    fun warn(message: String)

    fun error(message: String)

    fun success(message: String)

    fun info(message: String)
}
