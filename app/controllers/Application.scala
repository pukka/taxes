package controllers

import play.api._
import play.api.mvc._

import play.api.data.Form
import play.api.data.Forms._

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
      val cost = data.money
      val eight = data.money * 1.08
      val five = data.money * 1.05

      val title = "消費税計算プログラム"
      val msg = "原価：" + cost + "円、8%の消費税：" + eight + "円、5%の消費税：" + five + "円"

    Ok(views.html.index(title, msg, form1))
  }

  def IncludingTax = Action { implicit request =>
    val data: FormData = form1.bindFromRequest.get
    val cost = data.money / 1.05
    val five = data.money
    val eight = cost * 1.08
    val title = "消費税計算プログラム"
    val msg = "原価：" + cost + "円、8%の消費税：" + eight + "円、5%の消費税：" + five + "円"

    Ok(views.html.index(title, msg, form1))

  }

}
