package models

import org.joda.time.DateTime

case class Todo(id: Option[Long] = None, title: String, createdAt: DateTime = DateTime.now())
