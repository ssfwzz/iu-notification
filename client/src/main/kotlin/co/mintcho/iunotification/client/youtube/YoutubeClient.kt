package co.mintcho.iunotification.client.youtube

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "youtube",
    url = "https://www.googleapis.com/youtube/v3",
    configuration = [YoutubeClientConfig::class]
)
interface YoutubeClient {
    @GetMapping("/playlistItems?part=snippet&playlistId={playlistId}")
    fun playlistItems(@PathVariable playlistId: String): YoutubeListResponse<YoutubePlaylistItem>
}
