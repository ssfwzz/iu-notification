package co.mintcho.iunotification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class SchedulerApplication

fun main(args: Array<String>) {
    runApplication<SchedulerApplication>(*args)
}
