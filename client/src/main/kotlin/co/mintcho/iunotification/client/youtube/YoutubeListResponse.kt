package co.mintcho.iunotification.client.youtube

data class YoutubeListResponse<T>(
    val kind: String,
    val etag: String,
    val nextPageToken: String?,
    val prevPageToken: String?,
    val pageInfo: PageInfo,
    val items: List<T>
) {
    data class PageInfo(
        val totalResults: Int,
        val resultsPerPage: Int
    )
}
