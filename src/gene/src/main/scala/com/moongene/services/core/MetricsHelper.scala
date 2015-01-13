//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.services.core

import concurrent.Future
import reactivemongo.core.commands.LastError
import com.moongene.Core
import com.moongene.models.messages.DBLoad

//Utility trait to catch results from DB queries and approriately decrement current load and check for errors
trait MetricsHelper extends ExecutionTrait {
  import Implicit._
  def dbCallMetric(call: Future[LastError], lastErrorCheck: Boolean = true, recoverCheck: Boolean = true) = {
    Core.metricsLogger ! DBLoad(out = 1, cur = 1)
    call.map({ lastError =>
      Core.metricsLogger ! DBLoad(cur = -1, err = if (!lastError.ok && lastErrorCheck) 1 else 0)
    }).recover {
      case _ => if(recoverCheck) { Core.metricsLogger ! DBLoad(cur = -1, err = 1) }
    }
  }
}
