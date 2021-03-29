package controllers

import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import models.{Activity, ActivityRepository}
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SearchApiController @Inject()(val activityRpo: ActivityRepository,
                                    val controllerComponents: ControllerComponents)
                                   (implicit ec: ExecutionContext)
  extends BaseController with LazyLogging {

  def index(wordOpt: Option[String]) = Action.async { implicit request =>
    wordOpt match {
      case Some(word) =>
        activityRpo.search(word.toLowerCase).map { results =>
          if (results.isEmpty) {
            Ok("Match not found")
          } else {
            Ok(results.mkString("\n"))
          }
        }
      case None =>
        Future.successful(Ok("Hello!"))
    }
  }

  def addWord(word: String) = Action.async { implicit request =>
    activityRpo.create(Activity(text = word.toLowerCase)).map { id =>
      Ok(Json.toJson(id))
    }
  }

}
