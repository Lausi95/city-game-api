package net.lausi95.citygame.infrastructure.web

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.lausi95.citygame.domain.Tenant
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TenantFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tenant: String = request.getHeader("X-Tenant") ?: "default"
        request.setAttribute("tenant", Tenant(tenant))
        filterChain.doFilter(request, response)
    }
}
