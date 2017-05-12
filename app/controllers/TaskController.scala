package controllers

import javax.inject._

import models.Task
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

  def all = Action.async { implicit request =>
    taskRepository.all().map { case (tasks) =>
      val json = Json.toJson(tasks)
      Ok(json)
    }
  }
}
