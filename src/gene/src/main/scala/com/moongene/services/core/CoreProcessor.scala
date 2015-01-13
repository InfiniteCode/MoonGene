//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.services.core

import akka.actor.{RootActorPath, ActorLogging, Actor}
import akka.cluster.{MemberStatus, Cluster, Member}
import com.moongene.models.track._
import com.moongene.services.Database
import reactivemongo.bson.{BSONArray, BSONDocument}
import concurrent.{ExecutionContext, Future}
import reactivemongo.api.collections.default.BSONCollection
import org.joda.time.{DateTimeZone, DateTime}
import reactivemongo.core.commands.GetLastError
import com.moongene.Core
import com.moongene.models.messages.SysLoad

//Core processor receives a message and selects corresponding processor to execute real logic
class CoreProcessor extends Actor with ActorLogging with ExecutionTrait
        with StartProcessor
        with ExitProcessor
        with StateChangeProcessor
        with StateEventProcessor
        with EconomyEventProcessor
        with EconomyBalanceProcessor
        with FirstSessionProcessor
        with MetricsHelper {

  import Implicit._

  val cluster = Cluster(context.system)
  val dataDB = Database.getDatabase("gate")
  val statsColl = dataDB.collection("stats")

  //In case billing is required, this method increments metrics in DB
  def updateBillingStats(appId: String, amount: Int = 1) {
    dbCallMetric(statsColl.update(BSONDocument("_id" -> appId),
      BSONDocument(
        "$inc" -> BSONDocument(
          Common.getBillingCounterPath(DateTime.now.toDateTime(DateTimeZone.UTC)) -> amount
        )
      ), GetLastError(), upsert = true))
  }

  def receive = {
    case appStart : StartObj.Start => {
      processStart(dataDB, appStart, statsColl)
      updateBillingStats(appStart.sys.auth.appId)
      Core.metricsLogger ! SysLoad(0, 1, -1)
    }

    case appExit : ExitObj.Exit => {
      processExit(dataDB, appExit, statsColl)
      updateBillingStats(appExit.auth.appId)
      Core.metricsLogger ! SysLoad(0, 1, -1)
    }

    case appStateChange: StateChangeObj.StateChange => {
      processStateChange(dataDB, appStateChange, statsColl)
      updateBillingStats(appStateChange.auth.appId)
      Core.metricsLogger ! SysLoad(0, 1, -1)
    }

    case appStateEvent: StateEventObj.StateEvent => {
      processStateEvent(dataDB, appStateEvent, statsColl)
      updateBillingStats(appStateEvent.auth.appId)
      Core.metricsLogger ! SysLoad(0, 1, -1)
    }

    case economyEvent: EconomyEventObj.EconomyEvent => {
      processEconomyEvent(dataDB, economyEvent, statsColl)
      updateBillingStats(economyEvent.sys.auth.appId)
      Core.metricsLogger ! SysLoad(0, 1, -1)
    }

    case economyBalance: EconomyBalanceObj.EconomyBalance => {
      processEconomyBalance(dataDB, economyBalance, statsColl)
      updateBillingStats(economyBalance.auth.appId)
      Core.metricsLogger ! SysLoad(0, 1, -1)
    }

    case firstSessionEvent: FirstSessionEventObj.FirstSessionEvent => {
      processFirstSessionEvent(dataDB, firstSessionEvent, statsColl)
      updateBillingStats(firstSessionEvent.auth.appId)
      Core.metricsLogger ! SysLoad(0, 1, -1)
    }

  }
}
