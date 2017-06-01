package app.routes

import org.http4k.contract.ServerRoute

/**
 *
 * @author nsoushi
 */
interface ApplicationRoute {

    operator fun invoke(): List<ServerRoute>
}