package app.model

import org.http4k.core.Response

/**
 *
 * @author nsoushi
 */
interface ApplicationOutbound<in T> {
    operator fun invoke(model: T): Response
}