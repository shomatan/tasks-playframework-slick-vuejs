package repositories

import java.sql.Timestamp
import javax.inject.Inject

import models.Task
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future


class TaskRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Tasks = TableQuery[TaskTable]

  def all(): Future[Seq[Task]] = db.run(Tasks.result)

  def insert(task: Task): Future[Unit] = db.run(Tasks += task).map { _ => () }

  def insert(tasks: Seq[Task]): Future[Unit] = db.run(this.Tasks ++= tasks).map(_ => ())

  def findById(id: Long): Future[Option[Task]] = db.run(Tasks.filter(_.id === id).result.headOption)

  def update(id: Long, task: Task): Future[Unit] = {
    val taskToUpdate: Task = task.copy(Some(id))
    db.run(Tasks.filter(_.id === id).update(taskToUpdate)).map(_ => ())
  }

  def delete(id: Long): Future[Unit] =
    db.run(Tasks.filter(_.id === id).delete).map(_ => ())

  def count(): Future[Int] = {
    db.run(Tasks.map(_.id).length.result)
  }

  private class TaskTable(tag: Tag) extends Table[Task](tag, "tasks") {
    //implicit def dateTime = MappedColumnType.base[DateTime, Timestamp] (
    //  dt => new Timestamp(dt.getMillis), ts => new DateTime(ts.getTime)
    //)
    implicit val dateColumnType = MappedColumnType.base[DateTime, Long](d => d.getMillis, d => new DateTime(d))

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def content = column[String]("content")
    def createdAt = column[DateTime]("created_at")
    def deadlineAt = column[DateTime]("deadline_at")

    def * = (id.?, title, content, createdAt, deadlineAt) <> (Task.tupled, Task.unapply _)
  }

}
