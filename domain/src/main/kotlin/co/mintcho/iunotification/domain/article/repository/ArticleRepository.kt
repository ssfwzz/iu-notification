package co.mintcho.iunotification.domain.article.repository

import co.mintcho.iunotification.domain.article.model.ArticleDto
import co.mintcho.iunotification.domain.article.model.ArticleNoDto

interface ArticleRepository {
    fun getAllArticleNo(): List<ArticleNoDto>

    fun findByArticleNo(articleNo: ArticleNoDto): ArticleDto?
}
