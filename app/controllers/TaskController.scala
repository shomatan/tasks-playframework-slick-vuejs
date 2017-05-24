package controllers

import javax.inject._

import models.Task
import org.joda.time.DateTime
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller}
import repositories.TaskRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TaskJsonController {
  case class TaskForm(id: Option[Long], title: String, content: String, createdAt: Option[DateTime], deadlineAt: DateTime)

  implicit val yourJodaDateReads = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  implicit val yourJodaDateWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

  implicit val taskFormFormat = (
      (__ \ "id"       ).readNullable[Long] and
      (__ \ "title"    ).read[String]       and
      (__ \ "content"  ).read[String]       and
      (__ \ "createdAt").readNullable[DateTime] and
      (__ \ "deadlineAt").read[DateTime]
    )(TaskForm)

  implicit val tasksRowWritesWrites = (
      (__ \ "id"       ).writeNullable[Long] and
      (__ \ "title"    ).write[String]       and
      (__ \ "content"  ).write[String]       and
      (__ \ "createdAt").write[DateTime]     and
      (__ \ "deadlineAt").write[DateTime]
    )(unlift(Task.unapply))
}

@Singleton
class TaskController @Inject()(taskRepository: TaskRepository) extends Controller {

  import TaskJsonController._
  
  def index = Action {
    Ok(views.html.index())
  }

  def tasks = Action.async { implicit request =>
    taskRepository.all().map { case (tasks) =>
      val json = Json.toJson(tasks)
      Ok(json)
    }
  }

  /** Describe the task form (used in both edit and create screens).*/
  val taskForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
      "createdAt" -> optional(jodaDate),
      "deadlineAt" -> jodaDate
    )(TaskForm.apply)(TaskForm.unapply))

  def addTask = Action.async(parse.json) { implicit request =>
    Logger.debug("In addTask")

    request.body.validate[TaskForm].map { form =>

      val task = Task(title = form.title, content = form.content, deadlineAt = form.deadlineAt)

      Logger.debug(task.toString)

      taskRepository.insert(
        task
      ).map(_ => Ok(Json.obj("result" -> "success")))
    }.recoverTotal { e =>
      Future {
        BadRequest(Json.obj("result" ->"failure", "error" -> JsError.toJson(e)))
      }
    }
  }

  def edit(id: Long) = Action.async { implicit request =>
    val task = taskRepository.findById(id)

    task.map { case (ph) =>
      ph match {
        case Some(p) => {
          val json = Json.toJson(p)
          Ok(json)
        }
        case None => Ok("error")
      }
    }
  }

  def editTask(id: Long) = Action.async(parse.json) { implicit request =>
    Logger.debug("In editTask")

    request.body.validate[TaskForm].map { form =>

      val task = Task(title = form.title, content = form.content, deadlineAt = form.deadlineAt)

      Logger.debug(task.toString)

      taskRepository.update(id, task).map(_ => Ok("success"))
    }.recoverTotal { e =>
      Future {
        BadRequest(Json.obj("result" ->"failure", "error" -> JsError.toJson(e)))
      }
    }
  }

  def deleteTask(id: Long) = Action.async { implicit rs =>
    for {
      _ <- taskRepository.delete(id)
    } yield Ok("success")
  }
}
