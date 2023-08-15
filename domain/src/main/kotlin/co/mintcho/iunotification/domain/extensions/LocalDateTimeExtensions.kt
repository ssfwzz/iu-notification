package co.mintcho.iunotification.domain.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toFormattedDateTime(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}
