package net.lausi95.citygame.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TenantTest {

    @Test
    fun `parses tenant from prod like hostname`() {
        val tenant = Tenant.parse("go7.api.misterx.com")
        assertThat(tenant).isEqualTo(Tenant("go7"))
    }

    @Test
    fun `parses tenant from localhost`() {
        val tenant = Tenant.parse("localhost")
        assertThat(tenant).isEqualTo(Tenant("localhost"))
    }
}
