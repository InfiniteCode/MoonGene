//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.models.sys

import org.joda.time.DateTimeZone

//Used by AppsVault - a service to detect new apps and notify nodes about changes
object SysAppsVault {
  sealed trait SysAppsVaultMsg
  case class SelfUpdateAllApps() extends SysAppsVaultMsg
  case class SelfCheckUpdateDoc() extends SysAppsVaultMsg
  case class AppVaultDetails(token: String, timezoneStr: String, timezone: DateTimeZone)
  case class GetApps() extends SysAppsVaultMsg
  case class AllAppsMap(map: Map[String, AppVaultDetails]) extends SysAppsVaultMsg
  case class NewApp(id: String, details: AppVaultDetails) extends SysAppsVaultMsg
  case class UpdateApp(id: String, details: AppVaultDetails) extends SysAppsVaultMsg
  case class DelApp(id: String) extends SysAppsVaultMsg
}
