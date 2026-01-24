package net.lausi95.citygame.infrastructure.web

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jboss.logging.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class LoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val traceId = UUID.randomUUID()
        val user = SecurityContextHolder.getContext().authentication?.name
        val path = request.servletPath

        MDC.put("trace_id", traceId)
        MDC.put("user_id", user)
        MDC.put("path", path)

        filterChain.doFilter(request, response)
    }
}