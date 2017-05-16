package controllers

import play.api.mvc._
import play.api.routing._

class JsRouter extends Controller {

  def javascriptRoutes = Action { implicit request =>
    import routes.javascript._
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        TaskController.tasks,
        TaskController.addTask,
        TaskController.edit,
        TaskController.editTask
      )
    ).as("text/javascript")
  }

}
