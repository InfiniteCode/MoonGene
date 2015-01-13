//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.models.track

import org.joda.time.DateTime

object StateChangeObj {
  case class StateChange( deviceId: String,                  //User's device ID as string
                          deviceBinId: Option[Array[Byte]],  //User's device ID as byte array
                          version: String,                   //User defined application version
                          auth: Common.SysAuth,              //App authorization data
                          timestamp: DateTime,               //User's timestamp in UTC
                          newState: Common.StateInfo,        //User defined new state that the app is switching to
                          oldState: Common.StateInfo)        //User defined previous state
}

