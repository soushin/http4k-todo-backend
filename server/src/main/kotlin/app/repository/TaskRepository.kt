package app.repository

import app.ConflictException
import app.NotFoundException
import app.SystemException
import app.entity.Task
import app.entity.TaskEntity
import com.github.kittinunf.result.NoException
import com.github.kittinunf.result.Result
import io.requery.kotlin.eq
import io.requery.kotlin.gte
import io.requery.kotlin.lte
import io.requery.sql.KotlinEntityDataStore
import org.http4k.core.Status
import java.time.LocalDateTime

/**
 *
 * @author nsoushi
 */
class TaskRepository(val data: KotlinEntityDataStore<Any>) {

    fun findOneById(id: Long): Result<Task, TaskException> {
        return data.invoke {
            val query = select(Task::class) where (Task::id eq id)
            if (query.get().firstOrNull() == null)
                Result.Failure(TaskException.TaskNotFoundException("task not found. taskId:%d".format(id)))
            else
                Result.Success(query.get().first())
        }
    }

    fun findManyByDate(from: LocalDateTime, to: LocalDateTime): Result<List<Task>, TaskException> {
        return data.invoke {
            val query = select(Task::class) where (Task::createdAt gte from) and (Task::createdAt lte to)
            if (query.get().firstOrNull() == null)
                Result.Failure(TaskException.TaskNotFoundException("task not found. from:%s, to:%s".format(from, to)))
            else
                Result.Success(query.get().toList())
        }
    }

    fun insert(title: String): Result<Long, NoException> {
        val now = LocalDateTime.now()
        val entity = TaskEntity().apply {
            this.title = title
            this.createdAt = now
            this.updatedAt = now
        }

        data.invoke { insert(entity) }

        return Result.Success(entity.id)
    }

    fun update(id:Long, title: String): Result<Long, TaskException> {
        try {
            findOneById(id).fold({
                record ->
                if (record.finishedAt != null)
                    throw TaskException.TaskConflictException("conflict finishing task. taskId:%d".format(id))

                val now = LocalDateTime.now()
                record.title = title
                record.updatedAt = now
                data.invoke { update(record) }
            }, {
                error -> throw error
            })
            return Result.Success(id)
        } catch (e: TaskException) {
            return Result.Failure(e)
        }
    }

    fun delete(id: Long): Result<Boolean, TaskException> {
        try {
            findOneById(id).fold({
                record -> data.invoke { delete(record) }
            }, {
                error -> throw error
            })
            return Result.Success(true)
        } catch (e: TaskException) {
            return Result.Failure(e)
        }
    }

    fun finishedTask(id: Long): Result<Boolean, TaskException> {
        try {
            findOneById(id).fold({
                record ->
                    if (record.finishedAt != null)
                        throw TaskException.TaskConflictException("conflict finishing task. taskId:%d".format(id))

                    val now = LocalDateTime.now()
                    record.finishedAt = now
                    record.updatedAt = now
                    data.invoke { update(record) }
            }, {
                error -> throw error
            })
            return Result.Success(true)
        } catch (e: TaskException) {
            return Result.Failure(e)
        }
    }
}

sealed class TaskException : SystemException {

    constructor(message: String, status: Status) : super(message, status, null)

    class TaskNotFoundException : TaskException {
        constructor(message: String) : super(message, Status.NOT_FOUND)
    }

    class TaskConflictException : TaskException {
        constructor(message: String) : super(message, Status.CONFLICT)
    }

    companion object {
        fun handle(error: TaskException): SystemException {
            return when (error) {
                is TaskException.TaskNotFoundException -> NotFoundException(error.message!!)
                is TaskException.TaskConflictException -> ConflictException(error.message!!)
            }
        }
    }
}


