package logger

import mu.KotlinLogging

class KotlinLogger(private val mainTitle: String = "") : Logger {

    private val logger = KotlinLogging.logger(mainTitle)

    override fun error(message: String) = logger.error(message)

    override fun info(message: String) = logger.info(message)

    override fun success(message: String) = logger.trace(message)

    override fun warn(message: String) = logger.warn(message)
}