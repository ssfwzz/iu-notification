package co.mintcho.iunotification.consumer.sqs.service

import co.mintcho.iunotification.client.discord.DiscordClient
import co.mintcho.iunotification.client.discord.Payload
import co.mintcho.iunotification.client.discord.discordMessage
import co.mintcho.iunotification.consumer.sqs.message.DiscordNotificationMessage
import co.mintcho.iunotification.domain.article.model.ArticleDto
import co.mintcho.iunotification.domain.extensions.toFormattedDateTime
import co.mintcho.iunotification.domain.notification.model.NotificationType
import co.mintcho.iunotification.domain.notification.service.NotificationCommandService
import co.mintcho.iunotification.domain.video.model.VideoDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import feign.form.FormData
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.apache.commons.io.IOUtils
import org.springframework.stereotype.Service
import org.springframework.util.MimeTypeUtils
import java.io.ByteArrayOutputStream
import java.net.URL

private const val DISCORD_SEND_INTERVAL = 1000L

@Service
class DiscordNotificationService(
    private val notificationCommandService: NotificationCommandService,
    private val discordClient: DiscordClient,
    private val objectMapper: ObjectMapper
) {
    private val log = KotlinLogging.logger {}

    @CircuitBreaker(name = "DiscordNotificationService-send", fallbackMethod = "fallbackSend")
    fun send(message: DiscordNotificationMessage) {
        notificationCommandService.complete(message.id)

        val (payload, file) = when (message.type) {
            NotificationType.ARTICLE -> article(message)
            NotificationType.VIDEO -> video(message)
        }

        discordClient.sendMessage(objectMapper.writeValueAsString(payload), file)

        log.info { "completed sending message to discord.: $message" }

        Thread.sleep(DISCORD_SEND_INTERVAL)
    }

    private fun fallbackSend(message: DiscordNotificationMessage, ex: Throwable) {
        log.warn(ex) { "fallback method has been called.: $message" }

        throw ex
    }

    private fun article(message: DiscordNotificationMessage): Pair<Payload, FormData?> {
        val articleDto = objectMapper.readValue<ArticleDto>(message.payload)

        val file = runCatching {
            URL(articleDto.thumbnailUrl).openStream().use { inputStream ->
                ByteArrayOutputStream().use { outputStream ->
                    IOUtils.copy(inputStream, outputStream)
                    FormData(MimeTypeUtils.IMAGE_JPEG_VALUE, "thumbnail.jpg", outputStream.toByteArray())
                }
            }
        }

        val payload = discordMessage {
            content(articleDto.title)
            embed {
                title(articleDto.title)
                description(articleDto.description)
                color(0xa553cd)
                if (file.isSuccess) {
                    thumbnail { attachment(file.getOrThrow().fileName) }
                }
                footer("${articleDto.updatedTime.toFormattedDateTime()} ${articleDto.author}")
                url(articleDto.url)
            }
        }

        return Pair(payload, file.getOrNull())
    }

    private fun video(message: DiscordNotificationMessage): Pair<Payload, FormData?> {
        val videoDto = objectMapper.readValue<VideoDto>(message.payload)

        val payload = discordMessage {
            content("${videoDto.title} - ${videoDto.channelTitle}\n${videoDto.publishedAt.toFormattedDateTime()}\nhttps://youtu.be/${videoDto.externalId}")
        }

        return Pair(payload, null)
    }
}
