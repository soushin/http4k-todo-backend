package app.handler

import app.model.*
import app.service.*
import org.http4k.core.*

/**
 *
 * @author nsoushi
 */
class TaskHandler(private val getTaskService: GetTaskService,
                  private val getTaskListService: GetTaskListService,
                  private val createTaskService: CreateTaskService,
                  private val updateTaskService: UpdateTaskService,
                  private val deleteTaskService: DeleteTaskService,
                  private val finishTaskService: FinishTaskService) {

    fun getTask(request: Request): Response {
        val command = GetTaskInbound(request)
        return GetTaskOutbound(getTaskService(command))
    }

    fun getTaskList(request: Request): Response {
        val command = GetTaskListInbound(request)
        return GetTaskListOutbound(getTaskListService(command))
    }

    fun createTask(request: Request): Response {
        val command = CreateTaskInbound(request)
        val id = createTaskService(command)
        val task = getTaskService(GetTaskCommand(id))
        return GetTaskOutbound(task)
    }

    fun updateTask(request: Request): Response {
        val command = UpdateTaskInbound(request)
        val id = updateTaskService(command)
        val task = getTaskService(GetTaskCommand(id))
        return GetTaskOutbound(task)
    }

    fun deleteTask(request: Request): Response {
        val command = DeleteTaskInbound(request)
        return DeleteTaskOutbound(deleteTaskService(command))
    }

    fun finishTask(request: Request): Response {
        val command = FinishTaskInbound(request)
        return FinishTaskOutbound(finishTaskService(command))
    }
}