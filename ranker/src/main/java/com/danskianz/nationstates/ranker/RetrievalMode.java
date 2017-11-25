/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danskianz.nationstates.ranker;

/**
 * Modes of data retrieval.
 * 
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public enum RetrievalMode {
    DATA_DUMP,  /* Rely on the data dump */
    IMMEDIATE   /* Fetch directly from NationStates */
    ;
}
