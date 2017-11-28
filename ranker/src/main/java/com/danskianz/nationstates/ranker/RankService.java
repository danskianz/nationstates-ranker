/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danskianz.nationstates.ranker;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

/**
 * Provides rank and calculation functionality.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Contract
public interface RankService {

    public Map<String, Double> getRegionScores(String region,
            RetrievalMode mode,
            CalculationMode fetch);
    
    public double getNationScore(String nation,
            RetrievalMode mode,
            CalculationMode fetch);
}