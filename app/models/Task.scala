package models

import org.joda.time.DateTime

case class Task(id: Option[Long] = None, title: String, createdAt: DateTime = DateTime.now())
