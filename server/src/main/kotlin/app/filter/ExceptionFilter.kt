package app.filter

import app.routes.Error
import app.routes.ErrorItem
import app.SystemException
import org.http4k.core.*
import org.http4k.format.Jackson.auto

/**
 *
 * @author nsoushi
 */
class ExceptionFilter : Filter {
    override fun invoke(next: HttpHandler): HttpHandler = {
        try {
            val response = next(it)
            if (!response.status.successful)
                throw SystemException("request error", response.status, null)
            response
        } catch (e: Exception) {
            val status = when (e) {
                is SystemException -> e.status
                else -> Status.INTERNAL_SERVER_ERROR
            }
            val response = Body.auto<Error>().toLens()
            Response(status).with(response of Error(listOf(ErrorItem(e.message ?: "error", null, null))))
        }
    }
}