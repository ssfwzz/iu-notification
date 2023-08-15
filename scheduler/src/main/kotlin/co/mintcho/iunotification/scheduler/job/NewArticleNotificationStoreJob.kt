package co.mintcho.iunotification.scheduler.job

import co.mintcho.iunotification.domain.article.model.ArticleDto
import co.mintcho.iunotification.domain.article.service.ArticleQueryService
import co.mintcho.iunotification.domain.notification.model.NotificationType
import co.mintcho.iunotification.domain.notification.model.request.NotificationRequest
import co.mintcho.iunotification.domain.notification.service.NotificationCommandService
import co.mintcho.iunotification.domain.notification.service.NotificationQueryService
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

private const val ARTICLE_READ_INTERVAL = 1000L

@DisallowConcurrentExecution
class NewArticleNotificationStoreJob(
    private val articleQueryService: ArticleQueryService,
    private val notificationQueryService: NotificationQueryService,
    private val notificationCommandService: NotificationCommandService
) : QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        runBlocking {
            val newArticleNoList = articleQueryService.getAllArticleNo()
                .filter { !notificationQueryService.existsByExternalId(it.compositeId) }

            val articles = newArticleNoList.map {
                val article = articleQueryService.getArticle(it)
                delay(ARTICLE_READ_INTERVAL)
                article
            }

            articles.forEach { saveNotification(it) }
        }
    }

    private fun saveNotification(article: ArticleDto) {
        notificationCommandService.save(
            NotificationRequest(
                externalId = article.articleNo.compositeId,
                type = NotificationType.ARTICLE,
                payload = article
            )
        )
    }
}
