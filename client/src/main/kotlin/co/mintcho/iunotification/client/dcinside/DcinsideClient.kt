package co.mintcho.iunotification.client.dcinside

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "dcinside", url = "https://m.dcinside.com", configuration = [DcinsideClientConfig::class])
interface DcinsideClient {
    @GetMapping("/board/{galleryCode}?recommend=1")
    fun recommendPage(@PathVariable galleryCode: String): String

    @GetMapping("/board/{galleryCode}/{id}")
    fun articlePage(@PathVariable galleryCode: String, @PathVariable id: String): String
}
