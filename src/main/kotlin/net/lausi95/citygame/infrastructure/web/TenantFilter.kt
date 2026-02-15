package net.lausi95.citygame.infrastructure.web

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.lausi95.citygame.domain.Tenant
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private val log = KotlinLogging.logger { }

@Component
class TenantFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tenant = Tenant.parse(request.remoteHost)

        MDC.put("tenant", tenant.value)
        request.setAttribute("tenant", tenant)

        log.info { "Tenant determined" }

        filterChain.doFilter(request, response)
    }
}
