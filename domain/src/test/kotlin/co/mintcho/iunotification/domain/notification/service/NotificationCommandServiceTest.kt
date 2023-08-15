package co.mintcho.iunotification.domain.notification.service

import co.mintcho.iunotification.domain.notification.model.NotificationType
import co.mintcho.iunotification.domain.notification.model.entity.Notification
import co.mintcho.iunotification.domain.notification.model.request.NotificationRequest
import co.mintcho.iunotification.domain.notification.repository.NotificationRepository
import co.mintcho.iunotification.domain.video.model.VideoDto
import com.appmattus.kotlinfixture.kotlinFixture
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

class NotificationCommandServiceTest : StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val notificationRepository = mockk<NotificationRepository>()
    val objectMapper = mockk<ObjectMapper>()
    val applicationEventPublisher = mockk<ApplicationEventPublisher>()

    val notificationCommandService = NotificationCommandService(
        notificationRepository = notificationRepository,
        objectMapper = objectMapper,
        applicationEventPublisher = applicationEventPublisher
    )

    val fixture = kotlinFixture()

    "알림을 저장하고 알림 생성 이벤트를 발행한다" {
        val request = NotificationRequest(
            externalId = "externalId",
            type = NotificationType.VIDEO,
            payload = fixture<VideoDto>()
        )

        every { notificationRepository.save(any()) } returnsArgument 0
        every { objectMapper.writeValueAsString(any()) } returns "{}"
        justRun { applicationEventPublisher.publishEvent(any() as Any) }

        notificationCommandService.save(request)

        verify(exactly = 1) { notificationRepository.save(any()) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(any() as Any) }
    }

    "알림을 발송 완료 처리한다" {
        val notification = Notification(
            id = 1,
            externalId = "externalId",
            type = NotificationType.VIDEO,
            payload = "{}",
            published = false
        )

        every { notificationRepository.findById(any()) } returns notification
        every { notificationRepository.save(any()) } returnsArgument 0

        notificationCommandService.complete(1)

        notification.published.shouldBeTrue()
    }
})
