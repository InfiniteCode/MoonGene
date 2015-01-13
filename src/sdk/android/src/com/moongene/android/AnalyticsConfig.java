//Copyright 2014, Alex Khilko.
//This file is part of MoonGene which is released under MIT.
//See file LICENSE.TXT or go to www.alexkhilko.com for full license details. 

package com.moongene.android;

class AnalyticsConfig {
    // Enable to have DEBUG logging
    public static final boolean DEBUG = false;

    // Time interval in ms events are flushed at, or a limit we should reach to do flushing before time hit
    public static final long FLUSH_RATE = 30 * 1000;
    // Maximum queue length before flushing events
    public static final int  FLUSH_WAIT_LIMIT = 40;
    // How long to store events that were not sent for whatever reasons
    public static final int  DATA_EXPIRATION = 1000 * 60 * 60 * 48;

    // Where events will be sent to
    public static final String PROD_ENDPOINT = "http://gene.moongene.com";
    public static final String DEV_ENDPOINT =  "http://192.168.1.1:8080";
}
