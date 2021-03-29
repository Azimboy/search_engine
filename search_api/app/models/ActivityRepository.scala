package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ActivityRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)
                                  (implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class Activities(tag: Tag) extends Table[Activity](tag, "activities") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def text = column[String]("text")

    def * = (id.?, text) <> ((Activity.apply _).tupled, Activity.unapply)
  }

  private val activities = TableQuery[Activities]

  def create(activity: Activity): Future[Int] = db.run {
    activities returning activities.map(_.id) += activity
  }

  def search(word: String): Future[Seq[String]] = db.run {
    sql"""SELECT r.text
          FROM (SELECT text, text <-> '#${word}' AS dist FROM activities) r
          WHERE r.dist < 1
          ORDER BY r.dist LIMIT 10""".as[String]
  }

}