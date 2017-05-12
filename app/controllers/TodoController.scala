package controllers

import javax.inject._

import models.Todo
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repositories.TodoRepository

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class TodoController @Inject()(todoRepository: TodoRepository) extends Controller {

  implicit val todoWrites = Json.writes[Todo]

  def index = Action {
    Ok(views.html.index())
  }

  def all = Action.async { implicit request =>
    todoRepository.all().map { case (todos) =>
      val json = Json.toJson(todos)
      Ok(json)
    }
  }
}
