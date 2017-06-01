package app.client

import org.http4k.client.OkHttp
import org.http4k.core.*

/**
 *
 * @author nsoushi
 */
class AuthClient : BaseClient<String, String> {

    private val demoUserId = "3219472109384201"

    override fun invoke(param: String): String {
//        val uri = Uri.of("${getUrlBase()}${getPath()}?Authorization=%s".format(param))
//        val request = MemoryRequest(Method.GET, uri, uri.queries())
//        val response = OkHttp()(request)
//        return response.body.toString()
        return demoUserId
    }

    override fun getUrlBase()  = "http://localhost:9000"

    override fun getPath() = "/api/external"
}