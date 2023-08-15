package co.mintcho.iunotification.client.youtube

import java.time.LocalDateTime

data class YoutubePlaylistItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails?
) {
    data class Snippet(
        val publishedAt: LocalDateTime,
        val channelId: String,
        val title: String,
        val description: String,
        val channelTitle: String,
        val playlistId: String,
        val position: Int,
        val resourceId: ResourceId
    ) {
        data class Thumbnail(
            val url: String,
            val width: Int,
            val height: Int
        )

        data class ResourceId(
            val kind: String,
            val videoId: String
        )
    }

    data class ContentDetails(
        val videoId: String,
        val startAt: String,
        val endAt: String,
        val note: String,
        val videoPublishedAt: String
    )

    data class Status(
        val privacyStatus: String
    )
}
