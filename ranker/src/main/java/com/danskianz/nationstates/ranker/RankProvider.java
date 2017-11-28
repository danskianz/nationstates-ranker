/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danskianz.nationstates.ranker;

import com.github.agadar.nationstates.domain.common.CensusScore;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;

/**
 * Implements the task of ranking regions and nations.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Named("rankProvider")
@ApplicationScoped
@Service
public class RankProvider implements RankService {

    private final RegionProvider regionService;
    
    private final Map<String, Double> nationScoreMap;

    public RankProvider() {
        this.nationScoreMap = new ConcurrentHashMap<>();
        this.regionService = new RegionProvider();
    }

    @Override
    public Map<String, Double> getRegionScores(String region,
            RetrievalMode mode,
            CalculationMode fetch) {
        
        switch (fetch) {
            case REALTIME:
                Map<String, List<CensusScore>> regionCensus = regionService
                        .getRegionCensus(region, mode);
                
                return regionCensus.entrySet().stream().collect(Collectors.toMap(
                        nation -> nation.getKey(),
                        nation -> getNationScore(nation.getKey(), mode, fetch)));
                
            case CACHED:
                Map<String, List<String>> regionNationMap = regionService
                        .getRegionAndNationNames();
                
                if (!regionNationMap.containsKey(region)) {
                    return getRegionScores(region, mode,
                            CalculationMode.REALTIME);
                }
                
                List<String> nationList = regionNationMap.get(region);
                return nationList.stream().collect(Collectors.toMap(
                        nation -> nation,
                        nation -> getNationScore(nation, mode, fetch)));
            default:
                throw new AssertionError(fetch.name());
            
        }
        
    }

    @Override
    public double getNationScore(String nation,
            RetrievalMode mode,
            CalculationMode fetch) {

        switch (fetch) {
            case REALTIME:
                List<CensusScore> nationCensus = regionService
                        .getNationCensus(nation, mode);
                nationScoreMap.put(nation, Ranker.rank(nationCensus));
                break;

            case CACHED:
                if (!nationScoreMap.containsKey(nation)) {
                    return getNationScore(
                            nation, mode, CalculationMode.REALTIME);
                }
                break;

            default:
                throw new AssertionError(mode.name());

        }
        return nationScoreMap.get(nation);
    }

}
