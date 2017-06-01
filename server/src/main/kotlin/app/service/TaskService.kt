package app.service

import app.entity.Task
import app.repository.TaskException.Companion.handle
import app.repository.TaskRepository
import java.time.LocalDateTime

/**
 *
 * @author nsoushi
 */
data class TaskModel(val id: Long, val title: String, val finishedAt: LocalDateTime?, val createdAt: LocalDateTime, val updatedAt: LocalDateTime) {
    constructor(entity: Task):this(entity.id, entity.title, entity.finishedAt, entity.createdAt, entity.updatedAt)
}

data class GetTaskCommand(val id: Long)

class GetTaskService(private val taskRepository: TaskRepository) : ApplicationService<GetTaskCommand, TaskModel> {

    override fun invoke(command: GetTaskCommand): TaskModel {
        return taskRepository.findOneById(command.id).fold({
            task -> TaskModel(task)
        }, {
            error -> throw handle(error)
        })
    }
}

data class GetTaskListCommand(val from: LocalDateTime, val to: LocalDateTime)

class GetTaskListService(private val taskRepository: TaskRepository) : ApplicationService<GetTaskListCommand, List<TaskModel>> {

    override fun invoke(command: GetTaskListCommand): List<TaskModel> {
        return taskRepository.findManyByDate(command.from, command.to).fold({
            taskList -> taskList.map { TaskModel(it) }
        }, {
            error -> throw handle(error)
        })
    }
}

data class CreateTaskCommand(val title: String)

class CreateTaskService(private val taskRepository: TaskRepository) : ApplicationService<CreateTaskCommand, Long> {

    override fun invoke(command: CreateTaskCommand): Long {
        return taskRepository.insert(command.title).get()
    }
}

data class UpdateTaskCommand(val id: Long, val title: String)

class UpdateTaskService(private val taskRepository: TaskRepository) : ApplicationService<UpdateTaskCommand, Long> {

    override fun invoke(command: UpdateTaskCommand): Long {
        return taskRepository.update(command.id, command.title).get()
    }
}

data class DeleteTaskCommand(val id: Long)

class DeleteTaskService(private val taskRepository: TaskRepository) : ApplicationService<DeleteTaskCommand, Boolean> {

    override fun invoke(command: DeleteTaskCommand): Boolean {
        return taskRepository.delete(command.id).get()
    }
}

data class FinishTaskCommand(val id: Long)

class FinishTaskService(private val taskRepository: TaskRepository) : ApplicationService<FinishTaskCommand, Boolean> {

    override fun invoke(command: FinishTaskCommand): Boolean {
        return taskRepository.finishedTask(command.id).get()
    }
}
