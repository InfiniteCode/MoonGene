//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package models

import models.AccountAccessLevel._

/*
  Values stored in session
 */
case class SessionData(
  firstName: String,
  lastName: String,
  company: String,
  email: String,
  access: AccountAccessLevel)