package com.danskianz.nationstates.common;

/**
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public enum CalculationMode {
    REALTIME,   /* makes the roundtrip to NationStates */
    CACHED      /* stays with the cache if it exists */
    ;
}
