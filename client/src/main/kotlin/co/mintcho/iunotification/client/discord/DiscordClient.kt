package co.mintcho.iunotification.client.discord

import feign.form.FormData
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart

@FeignClient(name = "discord", url = "https://discord.com/api/webhooks", configuration = [DiscordClientConfig::class])
interface DiscordClient {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun sendMessage(@RequestPart("payload_json") payloadJson: String, @RequestPart file: FormData?)
}
