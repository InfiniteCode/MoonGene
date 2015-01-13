//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.models.track

object EconomyEventObj {
  case class EconomyEvent(deviceId: String,                 //User's device ID as string
                          deviceBinId: Option[Array[Byte]], //User's device ID as byte array
                          version: String,                  //User defined application version
                          sys: Common.Sys,                  //App Authorization, device information and geotime data

                          //Where event happened
                          state: Common.StateInfo,          //State information
                          timeline: String,                 //User defined Timeline ID
                          timeOffset: Int,                  //Offset on the timeline

                          //Economy info
                          paymentAmount: Long,              //Amount paid in cents
                          itemID: String,                   //User defined item ID
                          paymentCurrency: Option[String],  //Currency used for purchase
                          itemAmount: Option[Long],         //Amount of items
                          campaignID: Option[String],       //Campaign ID/Name that was active or none

                          //Purchase trigger data
                          preEvent: Option[List[String]],   //Events that preceded the economy event
                          preState: Option[List[String]])   //Corresponding states for events that preceded the economy event
}