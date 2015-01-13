//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.services.core

/*
  MoonGene Analytics includes GeoLite2 data created by MaxMind,
  available from http://www.maxmind.com
 */
import com.maxmind.geoip2.DatabaseReader

import java.io.{IOException, File}
import java.net.InetAddress
import com.maxmind.geoip2.exception.GeoIp2Exception
import com.moongene.models.track.Common.{IPGeoData, GeoUnit}
import com.typesafe.config.ConfigFactory

class GeoData {
  //Database file name is defined in a config file
  val dbReader = new DatabaseReader(new File(ConfigFactory.load().getString("moongene.geo.db")))

  //Convert IP to GeoData
  def get(ip: InetAddress): Option[IPGeoData] = {
    try {
      val data = dbReader.city(ip)
      val cityName = data.getCity.getName
      val cityCode = data.getCity.getGeoNameId
      val contName = data.getContinent.getName
      val contId =   data.getContinent.getGeoNameId
      val subdivName = data.getMostSpecificSubdivision.getName
      val subdivId = data.getMostSpecificSubdivision.getIsoCode

      Some(IPGeoData(continent = GeoUnit(if(contName != null) contName else "", if(contId != null) contId else 0),
                     country = GeoUnit(data.getCountry.getName, data.getCountry.getIsoCode),
                     division = GeoUnit(if(subdivName != null) subdivName else "", if(subdivId != null) subdivId else ""),
                     city = GeoUnit(if(cityName != null) cityName else "", if(cityCode != null) cityCode else 0)
      ))
    } catch {
      case io: IOException => None
      case geoIp: GeoIp2Exception => None
    }
  }
}
