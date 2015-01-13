//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.services.gene

import akka.actor.{RootActorPath, ActorSelection, Actor}
import akka.cluster.Cluster
import akka.cluster.Member
import akka.cluster.ClusterEvent.{MemberRemoved, MemberUp, CurrentClusterState, MemberEvent}
import collection.immutable

//This is a helper proxy for other actors to use AppsVault
class AppsVaultProxy extends Actor {

  override def preStart() {
    Cluster(context.system).subscribe(self, classOf[MemberEvent])
  }
  override def postStop() {
    Cluster(context.system).unsubscribe(self)
  }

  val ageOrdering = Ordering.fromLessThan[Member] { (a, b) ⇒ a.isOlderThan(b) }
  var membersByAge: immutable.SortedSet[Member] = immutable.SortedSet.empty(ageOrdering)

  def receive = {
    case state: CurrentClusterState ⇒
      membersByAge = immutable.SortedSet.empty(ageOrdering) ++ state.members.collect {
        case m ⇒ m
      }
    case MemberUp(m)         ⇒ membersByAge += m
    case MemberRemoved(m, _) ⇒ membersByAge -= m
    case other               ⇒ appValidator foreach { _.tell(other, sender) }
  }

  def appValidator: Option[ActorSelection] =
    membersByAge.headOption map (m ⇒ context.actorSelection(
      RootActorPath(m.address) / "user" / "app-validator-singleton" / "app-validator"))
}