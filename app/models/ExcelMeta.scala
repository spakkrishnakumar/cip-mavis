package models

case class ExcelMeta(
  customer: String,
  product: String,
  action: String,
  drugs: Seq[String]
)
