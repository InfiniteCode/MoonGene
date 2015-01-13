//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.models.track

import org.joda.time.DateTime

object ExitObj {
  case class Exit( deviceId: String,                  //User's device ID as string
                   deviceBinId: Option[Array[Byte]],  //User's device ID as byte array
                   version: String,                   //User defined application version
                   sessionLength: Long,               //Session length in seconds
                   timestamp: DateTime,               //User's timestamp in UTC
                   auth: Common.SysAuth,              //App authorization information
                   ip: Option[String],                //User's IP address
                   geoData: Option[Common.IPGeoData]) //User's geo data based on IP
}
