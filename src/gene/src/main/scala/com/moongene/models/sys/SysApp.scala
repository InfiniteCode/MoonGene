//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.models.sys

import reactivemongo.bson._

object SysApp {
  case class App(id: String)

  implicit val appFormat = Macros.handler[App]
}
