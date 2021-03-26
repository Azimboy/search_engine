package controllers

import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import models.{Activity, ActivityRepository}
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class SearchApiController @Inject()(val activityRpo: ActivityRepository,
                                    val controllerComponents: ControllerComponents)
                                   (implicit ec: ExecutionContext)
  extends BaseController with LazyLogging {

  def search(word: String) = Action.async { implicit request =>
    activityRpo.search(word).map { texts =>
      Ok(texts.mkString("\n"))
    }
  }

  def addWord(word: String) = Action.async { implicit request =>
    logger.info(s"Adding: $word")
    activityRpo.create(Activity(text = word)).map { text =>
      Ok(Json.toJson(text))
    }
  }

}
