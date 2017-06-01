package app.filter

import org.http4k.core.*
import org.http4k.filter.CorsPolicy
import org.http4k.lens.Header

/**
 *
 * @author nsoushi
 */
data class ApplicationCorsPolicy(val origins: List<String>, val headers: List<String>, val methods: List<Method>) {
    companion object {
        val UnsafeGlobalPermissive = CorsPolicy(listOf("*"), listOf("content-type", "api_key", "Authorization"), Method.values().toList())
    }
}

object ApplicationCorsFilter : Filter {
    private fun List<String>.joined() = this.joinToString(", ")

    override fun invoke(next: HttpHandler): HttpHandler = {
        val response = if (it.method == Method.OPTIONS) Response(Status.OK) else next(it)
        response.with(
                Header.required("access-control-allow-origin") of ApplicationCorsPolicy.UnsafeGlobalPermissive.origins.joined(),
                Header.required("access-control-allow-headers") of ApplicationCorsPolicy.UnsafeGlobalPermissive.headers.joined(),
                Header.required("access-control-allow-methods") of ApplicationCorsPolicy.UnsafeGlobalPermissive.methods.map { it.name }.joined()
        )
    }
}