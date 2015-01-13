//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.models.track

object StartObj {
  case class Start(deviceId: String,                  //User's device ID as string
                   deviceBinId: Option[Array[Byte]],  //User's device ID as byte array
                   version: String,                   //User defined application versions
                   sys: Common.Sys)                   //User's device, app authorization and geotime data
}

