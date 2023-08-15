package co.mintcho.iunotification.client.discord

fun discordMessage(block: DiscordMessageBuilder.() -> Unit): Payload {
    return DiscordMessageBuilder().apply(block).build()
}

data class Payload(
    val content: String = "",
    val embeds: List<Embed> = emptyList()
) {
    data class Embed(
        val title: String,
        val description: String,
        val color: Int,
        val thumbnail: Thumbnail?,
        val author: Author?,
        val footer: Footer?,
        val url: String?
    ) {
        data class Thumbnail(val url: String)
        data class Author(val name: String)
        data class Footer(val text: String)
    }
}

class DiscordMessageBuilder {
    private var content: String = ""
    private val embeds = mutableListOf<Payload.Embed>()

    fun content(content: String) {
        this.content = content
    }

    fun embed(block: EmbedBuilder.() -> Unit) {
        this.embeds.add(EmbedBuilder().apply(block).build())
    }

    fun build(): Payload {
        return Payload(
            content = this.content,
            embeds = this.embeds
        )
    }

    class EmbedBuilder {
        private lateinit var title: String
        private lateinit var description: String
        private var color: Int = 0
        private var thumbnail: Payload.Embed.Thumbnail? = null
        private var author: Payload.Embed.Author? = null
        private var footer: Payload.Embed.Footer? = null
        private var url: String? = null

        fun title(title: String) {
            this.title = title
        }

        fun description(description: String) {
            this.description = description
        }

        fun color(color: Int) {
            this.color = color
        }

        fun thumbnail(builder: ThumbnailBuilder.() -> Unit) {
            this.thumbnail = ThumbnailBuilder().apply(builder).build()
        }

        fun author(author: String) {
            this.author = Payload.Embed.Author(author)
        }

        fun footer(footer: String) {
            this.footer = Payload.Embed.Footer(footer)
        }

        fun url(url: String) {
            this.url = url
        }

        fun build() = Payload.Embed(
            title = title,
            description = description,
            color = color,
            thumbnail = thumbnail,
            author = author,
            footer = footer,
            url = url
        )

        class ThumbnailBuilder {
            private lateinit var url: String

            fun attachment(filename: String) {
                url = "attachment://$filename"
            }

            fun url(url: String) {
                this.url = url
            }

            fun build(): Payload.Embed.Thumbnail {
                return Payload.Embed.Thumbnail(url)
            }
        }
    }
}
