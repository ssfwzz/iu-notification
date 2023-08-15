package co.mintcho.iunotification.client.dcinside

import co.mintcho.iunotification.domain.article.model.ArticleDto
import co.mintcho.iunotification.domain.article.model.ArticleNoDto
import co.mintcho.iunotification.domain.article.repository.ArticleRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.jsoup.Jsoup
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

private const val IU_GALLERY_CODE = "iu_new"

@Repository
class IuGalleryArticleRepository(private val dcinsideClient: DcinsideClient) : ArticleRepository {
    private val log = KotlinLogging.logger {}

    @CircuitBreaker(
        name = "IuGalleryArticleRepository-getAllArticleNo",
        fallbackMethod = "fallbackGetAllArticleNo"
    )
    override fun getAllArticleNo(): List<ArticleNoDto> {
        val html = dcinsideClient.recommendPage(IU_GALLERY_CODE)
        val document = Jsoup.parse(html)

        return document.select(".gall-detail-lnktb a.lt")
            .map { Pattern.compile("/board/(.+)/(.+)\\?").matcher(it.attr("href")) }
            .filter { it.find() }
            .map { ArticleNoDto(galleryCode = it.group(1), externalId = it.group(2)) }
            .distinct()
            .reversed()
    }

    private fun fallbackGetAllArticleNo(ex: Throwable): List<ArticleNoDto> {
        log.warn(ex) { "fallback method has been called." }

        return emptyList()
    }

    @CircuitBreaker(name = "IuGalleryArticleRepository-findByArticleNo", fallbackMethod = "fallbackFindByArticleNo")
    override fun findByArticleNo(articleNo: ArticleNoDto): ArticleDto? {
        val html = dcinsideClient.articlePage(galleryCode = articleNo.galleryCode, id = articleNo.externalId)
        val document = Jsoup.parse(html)
        val titleBox = document.selectFirst(".gallview-tit-box")
        val created = titleBox!!.selectFirst(".ginfo2")
        val author = created!!.select("li:nth-child(1)").text()
        val title = document.select("meta[property='og:title']").attr("content")
        val description = document.select("meta[property='og:description']").attr("content")
        val thumbnailUrl = document.select("meta[property='og:image']").attr("content")
        val updatedTime = LocalDateTime.parse(
            created.select("li:nth-child(2)").text(),
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
        )
        val recommend = document.selectFirst("#recommend_join.on") != null
        val url = document.select("meta[property='og:url']").attr("content")

        return ArticleDto(
            articleNo = articleNo,
            title = title,
            author = author,
            description = description,
            thumbnailUrl = thumbnailUrl,
            updatedTime = updatedTime,
            recommend = recommend,
            url = url
        )
    }

    private fun fallbackFindByArticleNo(articleNo: ArticleNoDto, ex: Throwable): ArticleDto? {
        log.warn(ex) { "fallback method has been called.: $articleNo" }

        return null
    }
}
