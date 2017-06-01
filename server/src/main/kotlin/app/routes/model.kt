package app.routes

/**
 *
 * @author nsoushi
 */
data class Success<T>(
        val data: T
)

data class Error(
        val errors: List<ErrorItem>
)

data class ErrorItem(
        val message: String,
        val errorCode: String?,
        val field: String?
)