package app

import app.routes.ErrorItem
import org.http4k.core.Status
import org.slf4j.LoggerFactory

/**
 *
 * @author nsoushi
 */
open class SystemException(message: String, status: Status, errorItems: List<ErrorItem>?, ex: Exception?) : RuntimeException(message, ex) {

    val log = LoggerFactory.getLogger(SystemException::class.java)

    constructor(message: String, status: Status, errorItems: List<ErrorItem>?): this(message, status, errorItems, null)

    val status: Status by lazy {
        status
    }

    val errorItems: List<ErrorItem> by lazy {
        errorItems ?: listOf(ErrorItem(message, null, null))
    }
}

open class NotFoundException : SystemException {
    constructor(message: String) : super(message, Status.NOT_FOUND, null)
    constructor(message: String, errorItems: List<ErrorItem>?) : super(message, Status.NOT_FOUND, errorItems)
}

open class BadRequestException : SystemException {
    constructor(message: String) : super(message, Status.BAD_REQUEST, null)
    constructor(message: String, errorItems: List<ErrorItem>?) : super(message, Status.BAD_REQUEST, errorItems)
}

open class UnAuthorizedException : SystemException {
    constructor(message: String) : super(message, Status.UNAUTHORIZED, null)
    constructor(message: String, errorItems: List<ErrorItem>?) : super(message, Status.BAD_REQUEST, errorItems)
}

open class ConflictException : SystemException {
    constructor(message: String) : super(message, Status.CONFLICT, null)
    constructor(message: String, errorItems: List<ErrorItem>?) : super(message, Status.CONFLICT, errorItems)
}