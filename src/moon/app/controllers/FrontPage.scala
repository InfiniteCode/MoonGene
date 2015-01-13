//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package controllers

import play.api.mvc.{Action, Controller}
import models.DataAccess
import play.api.data.Form
import play.api.data.Forms._

/*
  FrontPage:
    - front page functionality
 */

object FrontPage extends Controller with DataAccess {
  val contactUsForm = Form(
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )
  )

  def about = Action { implicit request =>
    Ok(views.html.front.about())
  }

  def features = Action { implicit request =>
    Ok(views.html.front.features())
  }
}
