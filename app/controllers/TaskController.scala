package controllers

import javax.inject._

import models.Task
import org.joda.time.DateTime
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{ignored, longNumber, mapping, nonEmptyText, optional}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repositories.TaskRepository

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class TaskController @Inject()(taskRepository: TaskRepository) extends Controller {

  implicit val todoWrites = Json.writes[Task]

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
      "createdAt" -> ignored[DateTime](DateTime.now()))(Task.apply)(Task.unapply))

  def addTask = Action.async { implicit request =>
    Logger.debug("In addTask")

    val task: Task = taskForm.bindFromRequest.get
    taskRepository.insert(task).map(_ => Ok("success"))

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

    val task: Task = taskForm.bindFromRequest.get
    taskRepository.update(id, task).map(_ => Ok("success"))
  }

  def deleteTask(id: Long) = Action.async { implicit rs =>
    for {
      _ <- taskRepository.delete(id)
    } yield Ok("success")
  }
}
