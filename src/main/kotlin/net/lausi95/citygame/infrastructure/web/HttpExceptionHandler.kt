package net.lausi95.citygame.infrastructure.web

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.http.ProblemDetail
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

private val log = KotlinLogging.logger { }

@RestControllerAdvice
class HttpExceptionHandler(
    private val messageSource: MessageSource
) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ProblemDetail {
        val details = ProblemDetail.forStatus(400)
        details.setProperty("details", "Validation Error")
        ex.bindingResult.fieldErrors.forEach {
            details.setProperty(it.field, getMessage(it.defaultMessage))
        }
        return details
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(): ProblemDetail {
        log.warn { "Request with invalid JSON" }
        val problemDetail = ProblemDetail.forStatus(400)
        problemDetail.setProperty("details", "Input Error: Invalid JSON")
        return problemDetail
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ProblemDetail {
        log.error(ex) { "Unexpected Error (${ex.javaClass.simpleName}): ${ex.message}" }
        return ProblemDetail.forStatus(500)
    }

    fun getMessage(code: String?): String {
        if (code == null) {
            return "Invalid Value"
        }
        try {
            return messageSource.getMessage(code, arrayOf(), Locale.getDefault())
        } catch (ex: Exception) {
            log.error(ex) { "Invalid i18n code: $code" }
            return "Invalid Value"
        }
    }
}
