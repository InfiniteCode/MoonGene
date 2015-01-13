//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.models.messages

sealed trait SysMessage
object SysMetricsLog extends SysMessage

case class SysLoad(in: Int = 0, out: Int = 0, cur: Int = 0) extends SysMessage
case class DBLoad(out: Int = 0, cur: Int = 0, err: Int = 0) extends SysMessage
