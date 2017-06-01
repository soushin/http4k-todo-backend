package app.client

/**
 *
 * @author nsoushi
 */
interface BaseClient<in Param, out Response> {
    operator fun invoke(param: Param): Response

    fun getUrlBase(): String
    fun getPath(): String
}