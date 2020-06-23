package controllers

import adaptors.Adaptor.ContentOps
import commons.{ ErrorCode, Failure }
import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents, val mailer: Mailer)
  extends BaseController {

  def create(): Action[AnyContent] =
    Action { implicit request =>
      (request.body.toExcelMeta match {
        case Some(value) => mailer.sendMail(value)
        case None        => Failure(EmailSendError)
      }).fold(id => Ok(s"Success: $id"), _ => BadGateway("Error while sending mail"))
    }

}

case object EmailSendError extends ErrorCode
