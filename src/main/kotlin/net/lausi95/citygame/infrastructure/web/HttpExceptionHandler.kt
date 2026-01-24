package net.lausi95.citygame.infrastructure.web

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.http.ProblemDetail
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger { }

@RestControllerAdvice
class HttpExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ProblemDetail {
        val details = ProblemDetail.forStatus(400)
        details.setProperty("details", "Validation Error")
        ex.bindingResult.fieldErrors.forEach {
            details.setProperty(it.field, it.defaultMessage ?: "Invalid Value")
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

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(400)
        problemDetail.setProperty("details", ex.message)
        return problemDetail
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ProblemDetail {
        log.error(ex) { "Unexpected Error (${ex.javaClass.simpleName}): ${ex.message}" }
        return ProblemDetail.forStatus(500)
    }
}
