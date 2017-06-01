package app.model

import org.http4k.core.Request

/**
 *
 * @author nsoushi
 */
interface ApplicationInbound <out T> {
    operator fun invoke(request: Request): T
}