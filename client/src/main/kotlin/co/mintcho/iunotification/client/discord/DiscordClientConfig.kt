package co.mintcho.iunotification.client.discord

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class DiscordClientConfig {
    @Bean
    fun requestInterceptor(@Value("\${discord-client-webhook}") webhook: String): RequestInterceptor {
        return RequestInterceptor {
            it.uri(webhook)
        }
    }
}
