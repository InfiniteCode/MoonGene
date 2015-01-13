//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.services.core

trait ExecutionTrait {
  import scala.concurrent.ExecutionContext

  object Implicit {
     implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
  }
}