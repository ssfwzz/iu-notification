package co.mintcho.iunotification.domain.article.service

import co.mintcho.iunotification.domain.article.model.ArticleDto
import co.mintcho.iunotification.domain.article.model.ArticleNoDto
import co.mintcho.iunotification.domain.article.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleQueryService(private val articleRepository: ArticleRepository) {
    fun getAllArticleNo(): List<ArticleNoDto> {
        return articleRepository.getAllArticleNo()
    }

    fun getArticle(articleNo: ArticleNoDto): ArticleDto {
        return articleRepository.findByArticleNo(articleNo)
            ?: throw IllegalArgumentException("게시물을 찾지 못했습니다. articleNo=$articleNo")
    }
}
