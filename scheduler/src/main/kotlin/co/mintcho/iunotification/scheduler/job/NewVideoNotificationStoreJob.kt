package co.mintcho.iunotification.scheduler.job

import co.mintcho.iunotification.domain.notification.model.NotificationType
import co.mintcho.iunotification.domain.notification.model.request.NotificationRequest
import co.mintcho.iunotification.domain.notification.service.NotificationCommandService
import co.mintcho.iunotification.domain.notification.service.NotificationQueryService
import co.mintcho.iunotification.domain.video.model.VideoDto
import co.mintcho.iunotification.domain.video.service.VideoQueryService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

@DisallowConcurrentExecution
class NewVideoNotificationStoreJob(
    private val videoQueryService: VideoQueryService,
    private val notificationQueryService: NotificationQueryService,
    private val notificationCommandService: NotificationCommandService
) : QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        val newVideoList = videoQueryService.getAll()
            .filter { !notificationQueryService.existsByExternalId(it.externalId) }

        newVideoList.forEach { saveNotification(it) }
    }

    private fun saveNotification(video: VideoDto) {
        notificationCommandService.save(
            NotificationRequest(
                externalId = video.externalId,
                type = NotificationType.VIDEO,
                payload = video
            )
        )
    }
}
