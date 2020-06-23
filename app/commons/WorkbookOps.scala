package commons

import java.io.ByteArrayOutputStream

import commons.Convenience.ReceiverOps
import org.apache.poi.ss.usermodel.{ Sheet, Workbook }
import org.apache.poi.xssf.usermodel.XSSFWorkbook

object WorkbookOps {

  def createWB = new XSSFWorkbook()

  implicit class WBOps(workbook: Workbook) {

    def withSheet(name: String)(apply: Sheet => Sheet): Workbook = {
      apply(workbook.createSheet(name))
      workbook
    }

    def asByteArray: Array[Byte] =
      new ByteArrayOutputStream().run { it => workbook.write(it) }.toByteArray

  }

  implicit class SheetOps(sheet: Sheet) {

    def fill(data: Seq[String]): Sheet = {
      data
        .map { value => (value, sheet.createRow(data indexOf value)) }
        .map { value => (value._1, value._2.createCell(0)) }
        .foreach { value => value._2.setCellValue(value._1) }
      sheet
    }

  }

}
