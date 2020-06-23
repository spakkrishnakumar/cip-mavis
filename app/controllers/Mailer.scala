package controllers

import java.text.SimpleDateFormat
import java.util.Date

import commons.Convenience.ApplyOps
import commons.Result.OnReceiver
import commons.WorkbookOps.{ SheetOps, WBOps, createWB }
import commons.{ ErrorCode, Result }
import javax.inject.Inject
import models.ExcelMeta
import org.apache.poi.ss.usermodel.Workbook
import play.api.libs.mailer._

class Mailer @Inject() (mailerClient: MailerClient) {

  /*def sendEmail: String = {
    val cid = "1234"
    val email = Email(
      "Simple email",
      "Mister FROM <from@email.com>",
      Seq("Miss TO <to@email.com>"),
      attachments = Seq(
        AttachmentData(
          "novartis_e2b_delete.xlsx",
          createFile.toByteArray,
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )
      ),
      bodyText = Some("A text message"),
      bodyHtml = Some(
        s"""<html><body><p>An <b>html</b> message with cid <img src="cid:$cid"></p></body></html>"""
      )
    )
    mailerClient.send(email)
  }*/

  def sendMail(excelMeta: ExcelMeta): Result[ErrorCode, String] =
    Email(
      "Simple email",
      "Mister FROM <from@email.com>",
      Seq("Miss TO <to@email.com>"),
      attachments = Seq(createAttachmentFrom(excelMeta)),
      bodyText = Some("A text message"),
      bodyHtml = Some(
        s"""<html><body><p>An <b>html</b>Check Attachment </p></body></html>"""
      )
    ).let { it => mailerClient.send(it) }.asSuccess

  def createAttachmentFrom(excelMeta: ExcelMeta): AttachmentData =
    AttachmentData(
      fileNameFrom(excelMeta),
      createWorkBookFrom(excelMeta.drugs).asByteArray,
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )

  def fileNameFrom(excelMeta: ExcelMeta): String =
    Seq(
      excelMeta.customer,
      excelMeta.product,
      excelMeta.action,
      new SimpleDateFormat("yyyyMMdd").format(new Date)
    ).reduce { (curr, next) =>
      s"${curr}_$next"
    }.concat(".xlsx")

  def createWorkBookFrom(data: Seq[String]): Workbook =
    createWB
      .withSheet("Sheet 1") { sheet =>
        sheet.fill("Active ingredient/INN" +: data)
      }

}
