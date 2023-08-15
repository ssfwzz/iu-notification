package co.mintcho.iunotification.scheduler

import co.mintcho.iunotification.scheduler.job.NewArticleNotificationStoreJob
import co.mintcho.iunotification.scheduler.job.NewVideoNotificationStoreJob
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val NEW_ARTICLE_NOTIFICATION_STORE_JOB_NAME = "newArticleNotificationStoreJob"
private const val NEW_VIDEO_NOTIFICATION_STORE_JOB_NAME = "newVideoNotificationStoreJob"

@Configuration
class SchedulerConfig {
    @Bean(NEW_ARTICLE_NOTIFICATION_STORE_JOB_NAME)
    fun newArticleNotificationStoreJob(): JobDetail {
        return JobBuilder.newJob(NewArticleNotificationStoreJob::class.java)
            .withIdentity(NEW_ARTICLE_NOTIFICATION_STORE_JOB_NAME)
            .storeDurably()
            .build()
    }

    @Bean
    fun newArticleNotificationStoreJobTrigger(@Qualifier(NEW_ARTICLE_NOTIFICATION_STORE_JOB_NAME) job: JobDetail): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(job)
            .withIdentity("${NEW_ARTICLE_NOTIFICATION_STORE_JOB_NAME}Trigger")
            .startNow()
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(15)
                    .repeatForever()
            )
            .build()
    }

    @Bean(NEW_VIDEO_NOTIFICATION_STORE_JOB_NAME)
    fun newVideoNotificationStoreJob(): JobDetail {
        return JobBuilder.newJob(NewVideoNotificationStoreJob::class.java)
            .withIdentity(NEW_VIDEO_NOTIFICATION_STORE_JOB_NAME)
            .storeDurably()
            .build()
    }

    @Bean
    fun newVideoNotificationStoreJobTrigger(@Qualifier(NEW_VIDEO_NOTIFICATION_STORE_JOB_NAME) job: JobDetail): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(job)
            .withIdentity("${NEW_VIDEO_NOTIFICATION_STORE_JOB_NAME}Trigger")
            .startNow()
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(60)
                    .repeatForever()
            )
            .build()
    }
}
