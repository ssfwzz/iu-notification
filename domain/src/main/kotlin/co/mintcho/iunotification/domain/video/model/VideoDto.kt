package co.mintcho.iunotification.domain.video.model

import java.time.LocalDateTime

data class VideoDto(
    val externalId: String,
    val playlistId: String,
    val channelTitle: String,
    val title: String,
    val description: String,
    val publishedAt: LocalDateTime
)
