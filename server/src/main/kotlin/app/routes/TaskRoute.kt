package app.routes

import app.handler.TaskHandler
import app.model.*
import org.http4k.contract.Route
import org.http4k.contract.ServerRoute
import org.http4k.core.*
import org.http4k.lens.Header

/**
 *
 * @author nsoushi
 */
class TaskRoute(private val taskHandler: TaskHandler) : ApplicationRoute {

    override fun invoke() = listOf<ServerRoute>(getTask(), getTaskList(), createTask(), updateTask(), deleteTask(), finishTask())

    private val authorization = Header.required("authorization")

    private fun getTask(): ServerRoute {

        val handler: HttpHandler = { taskHandler.getTask(it) }

        return Route("タスクの取得")
                .header(authorization)
                .query(GetTaskInbound.id)
                .returning("タスクの取得成功" to Status.OK)
                .returning("タスクの取得失敗" to Status.NOT_FOUND)
                .at(Method.GET) / "task" bind handler
    }

    private fun getTaskList(): ServerRoute {

        val handler: HttpHandler = { taskHandler.getTaskList(it) }

        return Route("タスクリストの取得")
                .header(authorization)
                .query(GetTaskListInbound.from)
                .query(GetTaskListInbound.to)
                .returning("タスクリストの取得成功" to Status.OK)
                .returning("タスクリストの取得失敗" to Status.NOT_FOUND)
                .at(Method.GET) / "tasks" bind handler
    }

    private fun createTask(): ServerRoute {

        val handler: HttpHandler = { taskHandler.createTask(it) }

        return Route("タスクの登録")
                .header(authorization)
                .query(CreateTaskInbound.title)
                .returning("タスクの登録成功" to Status.OK)
                .at(Method.POST) / "task" bind handler
    }

    private fun updateTask(): ServerRoute {

        val handler: HttpHandler = { taskHandler.updateTask(it) }

        return Route("タスクの更新")
                .header(authorization)
                .query(UpdateTaskInbound.id)
                .query(UpdateTaskInbound.title)
                .returning("タスクの更新成功" to Status.OK)
                .returning("タスクの取得失敗" to Status.NOT_FOUND)
                .returning("タスクの更新失敗" to Status.CONFLICT)
                .at(Method.PUT) / "task" bind handler
    }

    private fun deleteTask(): ServerRoute {

        val handler: HttpHandler = { taskHandler.deleteTask(it) }

        return Route("タスクの削除")
                .header(authorization)
                .query(DeleteTaskInbound.id)
                .returning("タスクの削除成功" to Status.OK)
                .returning("タスクの取得失敗" to Status.NOT_FOUND)
                .at(Method.DELETE) / "tasks" bind handler
    }

    private fun finishTask(): ServerRoute {

        val handler: HttpHandler = { taskHandler.finishTask(it) }

        return Route("タスクの完了")
                .header(authorization)
                .query(FinishTaskInbound.id)
                .returning("タスクの完了成功" to Status.OK)
                .returning("タスクの取得失敗" to Status.NOT_FOUND)
                .returning("タスクの完了失敗" to Status.CONFLICT)
                .at(Method.PUT) / "task" / "finish" bind handler
    }
}

