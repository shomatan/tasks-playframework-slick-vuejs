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

  case class TaskForm(id: Option[Long], title: String, content: String, deadlineAt: DateTime)

  implicit val yourJodaDateReads = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  implicit val yourJodaDateWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ss.SSSZ'")

  implicit val taskFormFormat = (
      (__ \ "id"       ).readNullable[Long] and
      (__ \ "title"    ).read[String]       and
      (__ \ "content"  ).read[String]       and
      //(__ \ "createdAt").read[DateTime] and
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
      "deadlineAt" -> ignored[DateTime](DateTime.now())
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

  def editTask(id: Long) = Action.async { implicit request =>
    Logger.debug("In editTask")

    val form: TaskForm = taskForm.bindFromRequest.get
    val task = Task(title = form.title, content = form.content, deadlineAt = form.deadlineAt)
    taskRepository.update(id, task).map(_ => Ok("success"))
  }

  def deleteTask(id: Long) = Action.async { implicit rs =>
    for {
      _ <- taskRepository.delete(id)
    } yield Ok("success")
  }
}
