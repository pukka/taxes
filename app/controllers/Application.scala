package controllers

import play.api._
import play.api.mvc._

import play.api.data.Form
import play.api.data.Forms._

import scala.collection._
import scala.collection.mutable._

import play.api.data.validation._

object Application extends Controller {

  case class FormData(money:Int)
  val form1 = Form(
    mapping(
	"money" -> number
    )(FormData.apply)(FormData.unapply)
  )

  def index = Action {
    val title = "消費税計算プログラム"
    val msg = "このページは4月からの増税用の計算ページです。"

    Ok(views.html.index(title, msg, form1))
  }

  def NotIncludingTax = Action { implicit request =>
      val data: FormData = form1.bindFromRequest.get
      val cost: BigDecimal = data.money
      val eight: BigDecimal = data.money * 1.08
      val five: BigDecimal = data.money * 1.05

      val title = "消費税計算プログラム"
      val msg = "原価：" + cost.toInt + "円、8%の消費税：" + eight.toInt + "円、5%の消費税：" + five.toInt + "円"

    Ok(views.html.index(title, msg, form1))
  }

  def IncludingTax = Action { implicit request =>
    val data: FormData = form1.bindFromRequest.get
    val cost: BigDecimal = data.money / 1.05
    val five: BigDecimal = data.money
    val eight: BigDecimal = cost * 1.08
    val title = "消費税計算プログラム"
    val msg = "原価：" + cost.toInt + "円、8%の消費税：" + eight.toInt + "円、5%の消費税：" + five.toInt + "円"

    Ok(views.html.index(title, msg, form1))
  }
}
