package app.util

import app.SystemException
import org.http4k.core.Status

/**
 *
 * @author nsoushi
 */
fun systemException(msg: String) = SystemException(msg, Status.INTERNAL_SERVER_ERROR, null)