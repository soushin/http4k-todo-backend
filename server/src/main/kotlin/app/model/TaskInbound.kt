package app.model

import app.service.*
import app.util.DateUtil
import app.util.DateUtil.to
import org.http4k.core.Request
import org.http4k.format.Jackson
import org.http4k.lens.Query

/**
 *
 * @author nsoushi
 */
object GetTaskInbound : ApplicationInbound<GetTaskCommand> {

    val id = Query.required("id")

    override fun invoke(request: Request) = GetTaskCommand(id(request).toLong())
}

object GetTaskListInbound : ApplicationInbound<GetTaskListCommand> {

    val from = Query.required("from")
    val to = Query.required("to")

    override fun invoke(request: Request) = GetTaskListCommand(from(request) to DateUtil.Format.FULL, to(request) to DateUtil.Format.FULL)
}

object CreateTaskInbound : ApplicationInbound<CreateTaskCommand> {

    val title = Query.required("title")

    override fun invoke(request: Request) = CreateTaskCommand(title(request))
}

object UpdateTaskInbound : ApplicationInbound<UpdateTaskCommand> {

    val id = Query.required("id")
    val title = Query.required("title")

    override fun invoke(request: Request) = UpdateTaskCommand(id(request).toLong(), title(request))
}

object DeleteTaskInbound : ApplicationInbound<DeleteTaskCommand> {

    val id = Query.required("id")

    override fun invoke(request: Request) = DeleteTaskCommand(id(request).toLong())
}

object FinishTaskInbound : ApplicationInbound<FinishTaskCommand> {

    val id = Query.required("id")

    override fun invoke(request: Request) = FinishTaskCommand(id(request).toLong())
}
