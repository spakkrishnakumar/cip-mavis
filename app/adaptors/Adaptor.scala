package adaptors

import models.ExcelMeta
import play.api.libs.json._
import play.api.mvc.AnyContent

object Adaptor {

  implicit class ContentOps(value: AnyContent) {
    def toExcelMeta: Option[ExcelMeta] =
      value.asJson match {
        case Some(value) => Some(value.excelMeta)
        case None        => None
      }
  }

  implicit class JsValueOps(value: JsValue) {

    def excelMeta: ExcelMeta = {
      val customer = value.get("customer")
      val product = value.get("product")
      val action = value.get("action")
      val drugs = value.getArray("drugs")

      ExcelMeta(customer, product, action, drugs)
    }

    def get(field: String): String = (value \ field).getOrElse(JsNull).as[String]

    def getArray(field: String): Seq[String] =
      (value \ field).getOrElse(JsNull) match {
        case JsArray(value) => value.map(_.as[String]).toIndexedSeq
        case _              => Seq.empty
      }

  }

}
