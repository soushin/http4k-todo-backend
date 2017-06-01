package app

import app.client.AuthClient
import app.config.AppConfig
import app.filter.ApplicationCorsFilter
import app.filter.AuthFilter
import app.filter.ExceptionFilter
import app.handler.TaskHandler
import app.repository.TaskRepository
import app.routes.TaskRoute
import app.service.*
import com.github.salomonbrys.kodein.*
import io.requery.sql.KotlinEntityDataStore
import org.http4k.contract.*
import org.http4k.core.*
import org.http4k.format.Jackson
import org.http4k.server.Jetty
import org.http4k.server.asServer

/**
 *
 * @author nsoushi
 */
fun main(args: Array<String>) {

    val module = RouteModule(Root / "api", Swagger(ApiInfo("http4k test API", "v1.0"), Jackson))
            .withDescriptionPath { it / "api-docs" }
            .withRoutes(kodein.instance<TaskRoute>("taskRoute")())
    val handler = module.toHttpHandler()

    val exceptionFilter = kodein.instance<ExceptionFilter>("exceptionFilter")
    val authFilter = kodein.instance<AuthFilter>("authFilter")

    val app = ApplicationCorsFilter.then(exceptionFilter).then(authFilter).then(handler)
    app.asServer(Jetty(9000)).start()
}

val kodein = Kodein {
    // config
    bind<KotlinEntityDataStore<Any>>("datasource") with singleton { AppConfig().kotlinEntityDataStore() }
    // client
    bind<AuthClient>("authClient") with singleton { AuthClient() }
    // filter
    bind<AuthFilter>("authFilter") with singleton { AuthFilter(instance("authClient")) }
    bind<ExceptionFilter>("exceptionFilter") with singleton { ExceptionFilter() }
    // repository
    bind<TaskRepository>("taskRepository") with singleton { TaskRepository(instance("datasource")) }
    // service
    bind<GetTaskService>("getTaskService") with singleton { GetTaskService(instance("taskRepository")) }
    bind<GetTaskListService>("getTaskListService") with singleton { GetTaskListService(instance("taskRepository")) }
    bind<CreateTaskService>("createTaskService") with singleton { CreateTaskService(instance("taskRepository")) }
    bind<UpdateTaskService>("updateTaskService") with singleton { UpdateTaskService(instance("taskRepository")) }
    bind<DeleteTaskService>("deleteTaskService") with singleton { DeleteTaskService(instance("taskRepository")) }
    bind<FinishTaskService>("finishTaskService") with singleton { FinishTaskService(instance("taskRepository")) }
    // handler
    bind<TaskHandler>("taskHandler") with singleton {
        TaskHandler(instance("getTaskService"),
                instance("getTaskListService"),
                instance("createTaskService"),
                instance("updateTaskService"),
                instance("deleteTaskService"),
                instance("finishTaskService")) }
    // routes
    bind<TaskRoute>("taskRoute") with singleton { TaskRoute(instance("taskHandler")) }
}





