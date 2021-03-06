//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package controllers

import models.DataAccess

import play.api._
import play.api.mvc._
import concurrent.{Future, Promise}
import org.joda.time.DateTime

/*
  Application controller:
    - main destination
    - redirects either to analytics or front page
    - untrails the last slash if it exists in the URL
 */

object Application extends Controller with DataAccess {
  def index = Action { implicit request =>
    val cookieEmail = request.cookies.get("login_email")
    val cookieToken = request.cookies.get("login_token")

    if (cookieEmail != None && cookieToken != None) {
      Async {
        val futureAcc = accountByEmail(cookieEmail.get.value)

        for {
          maybeAccount <- futureAcc
          result <- Promise.successful(maybeAccount.map { account =>
            if(account.tokens.filter(_ == cookieToken.get.value).size > 0) {
              accountUpdateLoginDate(account, DateTime.now, updateTokens = false)

              Redirect(routes.Analytics.dashboard("all")).withSession("email" -> account.email,
                                        "first_name" -> account.firstName,
                                        "last_name" -> account.lastName,
                                        "company" -> account.company,
                                        "access" -> account.accessLevel.toString)
            } else
              Ok(views.html.front.index())
          }).future

        } yield result.getOrElse(Ok(views.html.front.index()))
      }
    } else
      Ok(views.html.front.index())
  }

  def untrailSlash(path: String) = Action {
    MovedPermanently("/%s".format(path))
  }
}