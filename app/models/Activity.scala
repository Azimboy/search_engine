package models

import play.api.libs.json.Json

case class Activity(id: Option[Int] = None, text: String)

object Activity {
  implicit val textFormat = Json.format[Activity]
}
