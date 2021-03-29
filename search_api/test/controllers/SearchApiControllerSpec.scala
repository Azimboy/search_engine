package controllers

import models.ActivityRepository
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.{ExecutionContext, Future}

class SearchApiControllerSpec extends PlaySpec with MockitoSugar {

  implicit val ec = ExecutionContext.Implicits.global
  val activitiesRpo = mock[ActivityRepository]
  val controller = new SearchApiController(activitiesRpo, stubControllerComponents())

  "SearchApiControllerSpec" should {
    "successfully open index page" in {
      val result = controller.index(None)(FakeRequest())

      status(result) mustBe OK
      contentType(result) mustBe Some(TEXT)
      contentAsString(result) must include("Hello!")
    }

    "successfully return list of words from database" in {
      when(activitiesRpo.search(any())).thenReturn(Future.successful(Seq("word1", "word2")))
      val result = controller.index(Some("word"))(FakeRequest())

      status(result) mustBe OK
      contentType(result) mustBe Some(TEXT)
      contentAsString(result) must include("word1\nword2")
    }

    "successfully return match not found result" in {
      when(activitiesRpo.search(any())).thenReturn(Future.successful(Seq.empty[String]))
      val result = controller.index(Some("hello"))(FakeRequest())

      status(result) mustBe OK
      contentType(result) mustBe Some(TEXT)
      contentAsString(result) must include("Match not found")
    }

    "successfully add word with status OK" in {
      when(activitiesRpo.create(any())).thenReturn(Future.successful(1))
      val result = controller.addWord("word")(FakeRequest())

      status(result) mustBe OK
      contentType(result) mustBe Some(JSON)
      contentAsString(result) must include("1")
    }
  }
}
