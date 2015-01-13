//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package services

import org.apache.commons.mail.HtmlEmail
import play.api.Play
import concurrent.Future

import play.api.libs.concurrent.Execution.Implicits._
import java.util
import javax.mail.internet.InternetAddress

/*
  EmailService:
    - sends emails
 */

case class Config(
  tls: Boolean = false,
  ssl: Boolean = false,
  port: Int = 25,
  host: String,
  user: String,
  password: String
)

case class EmailMessage(
  to: String,
  from: String,
  cc: Option[String],
  replyTo: Option[String],
  topic: String,
  body: String,
  template: Option[String]
)

object EmailService {
  val port = Play.current.configuration.getInt("smtp.port").getOrElse(25)
  val host = Play.current.configuration.getString("smtp.host").get
  val user = Play.current.configuration.getString("smtp.user").get
  val password = Play.current.configuration.getString("smtp.password").get
  val tls = Play.current.configuration.getBoolean("smtp.tls").getOrElse(false)
  val ssl = Play.current.configuration.getBoolean("smtp.ssl").getOrElse(false)

  val defaultConfig = Config(tls, ssl, port, host, user, password)

  def sendEmail(message: EmailMessage, config: Config = defaultConfig): Future[String] = {
    Future {
      val email = new HtmlEmail()

      email.setStartTLSEnabled(config.tls)
      email.setSSLOnConnect(config.ssl)
      email.setSmtpPort(config.port)
      email.setHostName(config.host)
      email.setAuthentication(config.user, config.password)

      email.setHtmlMsg(message.body)
      email.setTextMsg(message.body)
      email.addTo(message.to)
      email.setFrom(message.from)
      email.setSubject(message.topic)
      if (!message.replyTo.isEmpty) {
        val addresses = new util.ArrayList[InternetAddress]()
        addresses.add(new InternetAddress(message.replyTo.get))
        email.setReplyTo(addresses)
      }

      if (message.cc != null)
        email.addCc(message.cc.get)

      email.send()
    }
  }
}
