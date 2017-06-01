package app.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 *
 * @author nsoushi
 */
object DateUtil {

    enum class Format(val format: String) {
        FULL("yyyy-MM-dd HH:mm:ss"),
        FULL_UTC("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }

    infix fun LocalDateTime.to(f: DateUtil.Format) : String = DateTimeFormatter.ofPattern(f.format).format(this)

    infix fun String.to(f: DateUtil.Format) : LocalDateTime = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(f.format))
}
