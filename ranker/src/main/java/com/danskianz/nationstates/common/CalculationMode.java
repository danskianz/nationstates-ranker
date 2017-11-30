/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
