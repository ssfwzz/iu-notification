package co.mintcho.iunotification.domain.article.model

import java.time.LocalDateTime

data class ArticleDto(
    val articleNo: ArticleNoDto,
    val title: String,
    val author: String,
    val description: String,
    val thumbnailUrl: String,
    val updatedTime: LocalDateTime,
    val recommend: Boolean,
    val url: String
)
