package co.mintcho.iunotification.client.dcinside

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders

private const val MOBILE_USER_AGENT =
    "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"

class DcinsideClientConfig {
    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor {
            it.header(HttpHeaders.USER_AGENT, MOBILE_USER_AGENT)
        }
    }
}
