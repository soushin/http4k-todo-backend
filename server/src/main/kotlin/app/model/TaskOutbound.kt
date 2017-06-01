package app.model

import app.routes.Success
import app.service.TaskModel
import app.util.DateUtil
import app.util.DateUtil.to
import org.http4k.core.Body
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto

/**
 *
 * @author nsoushi
 */

object GetTaskOutbound : ApplicationOutbound<TaskModel> {

    val response = Body.auto<Success<Context>>().toLens()

    data class Context(val id: Long, val title: String, val finishedAt: String?, val createdAt: String, val updatedAt: String) {
        constructor(taskModel: TaskModel) : this(
                taskModel.id,
                taskModel.title,
                taskModel.finishedAt?.to(DateUtil.Format.FULL),
                taskModel.createdAt to DateUtil.Format.FULL,
                taskModel.updatedAt to DateUtil.Format.FULL)
    }

    override fun invoke(model: TaskModel) = Response(Status.OK).with(response of Success(Context(model)))
}

object GetTaskListOutbound : ApplicationOutbound<List<TaskModel>> {

    private val response = Body.auto<Success<List<GetTaskOutbound.Context>>>().toLens()

    override fun invoke(model: List<TaskModel>) = Response(Status.OK).with(response of Success(model.map { GetTaskOutbound.Context(it) }))
}

object CreateTaskOutbound : ApplicationOutbound<TaskModel> {

    private val response = Body.auto<Success<TaskModel>>().toLens()

    override fun invoke(model: TaskModel) = Response(Status.OK).with(response of Success(model))
}

object UpdateTaskOutbound : ApplicationOutbound<TaskModel> {

    private val response = Body.auto<Success<TaskModel>>().toLens()

    override fun invoke(model: TaskModel) = Response(Status.OK).with(response of Success(model))
}

object DeleteTaskOutbound : ApplicationOutbound<Boolean> {

    private val response = Body.auto<Success<Boolean>>().toLens()

    override fun invoke(model: Boolean) = Response(Status.OK).with(response of Success(model))
}

object FinishTaskOutbound : ApplicationOutbound<Boolean> {

    private val response = Body.auto<Success<Boolean>>().toLens()

    override fun invoke(model: Boolean) = Response(Status.OK).with(response of Success(model))
}
