package co.mintcho.iunotification.domain.article.model

data class ArticleNoDto(
    val galleryCode: String,
    val externalId: String
) {
    val compositeId = "$galleryCode-$externalId"
}
