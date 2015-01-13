//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene

import com.typesafe.config.ConfigFactory

object GeneConfig {
  val config = ConfigFactory.load()
  val serverPort = config.getInt("gene.server.port")
  val serverHost = config.getString("gene.server.host")
}