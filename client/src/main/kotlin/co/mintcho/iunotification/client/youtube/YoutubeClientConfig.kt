package co.mintcho.iunotification.client.youtube

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class YoutubeClientConfig {
    @Bean
    fun requestInterceptor(@Value("\${youtube-client-api-key}") apiKey: String): RequestInterceptor {
        return RequestInterceptor {
            it.query("key", apiKey)
        }
    }
}
