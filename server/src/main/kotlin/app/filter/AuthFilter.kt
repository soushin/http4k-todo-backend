package app.filter

import app.UnAuthorizedException
import app.client.AuthClient
import org.http4k.core.*
import org.http4k.lens.Header

/**
 *
 * @author nsoushi
 */
class AuthFilter(val client: AuthClient) : Filter {
    private fun List<String>.joined() = this.joinToString(", ")
    override fun invoke(next: HttpHandler): HttpHandler = {

        if (!it.uri.path.startsWith("/api/") || it.uri.path == "/api/api-docs") {
            val response = next(it)
            response
        } else if (it.header("Authorization") != null) {
            val request = it.header("UserID", client(it.header("Authorization")!!))
            val response = next(request)
            response
        } else {
            throw UnAuthorizedException("unauthorized error")
        }
    }
}