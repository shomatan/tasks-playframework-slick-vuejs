package repositories

import java.sql.Timestamp
import javax.inject.Inject

import models.Todo
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future


class TodoRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Todos = TableQuery[TodoTable]

  def all(): Future[Seq[Todo]] = db.run(Todos.result)

  def insert(todo: Todo): Future[Unit] = db.run(Todos += todo).map { _ => () }

  def insert(todos: Seq[Todo]): Future[Unit] = db.run(this.Todos ++= todos).map(_ => ())

  def findById(id: Long): Future[Option[Todo]] = db.run(Todos.filter(_.id === id).result.headOption)

  def update(id: Long, todo: Todo): Future[Unit] = {
    val todoToUpdate: Todo = todo.copy(Some(id))
    db.run(Todos.filter(_.id === id).update(todoToUpdate)).map(_ => ())
  }

  def delete(id: Long): Future[Unit] =
    db.run(Todos.filter(_.id === id).delete).map(_ => ())

  def count(): Future[Int] = {
    db.run(Todos.map(_.id).length.result)
  }

  private class TodoTable(tag: Tag) extends Table[Todo](tag, "todos") {
    implicit def dateTime = MappedColumnType.base[DateTime, Timestamp] (
      dt => new Timestamp(dt.getMillis), ts => new DateTime(ts.getTime)
    )

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def createdAt = column[DateTime]("created_at")

    def * = (id.?, title, createdAt) <> (Todo.tupled, Todo.unapply _)
  }

}
