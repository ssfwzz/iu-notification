package co.mintcho.iunotification.client.youtube

import co.mintcho.iunotification.domain.video.model.VideoDto
import co.mintcho.iunotification.domain.video.repository.VideoRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Repository

private const val IU_RECENT_PLAYLIST = "PLGhOCcpfhWjf_EGjeaWqOqR9xz1M4WnTD"

@Repository
class IuYoutubeVideoRepository(private val youtubeClient: YoutubeClient) : VideoRepository {
    private val log = KotlinLogging.logger {}

    @CircuitBreaker(name = "IuYoutubeVideoRepository-getAll", fallbackMethod = "fallbackGetAll")
    override fun getAll(): List<VideoDto> {
        return youtubeClient.playlistItems(IU_RECENT_PLAYLIST)
            .items
            .map {
                VideoDto(
                    externalId = it.snippet.resourceId.videoId,
                    playlistId = it.snippet.playlistId,
                    channelTitle = it.snippet.channelTitle,
                    title = it.snippet.title,
                    description = it.snippet.description,
                    publishedAt = it.snippet.publishedAt.plusHours(9)
                )
            }
            .reversed()
    }

    private fun fallbackGetAll(ex: Throwable): List<VideoDto> {
        log.warn(ex) { "fallback method has been called." }

        return emptyList()
    }
}
