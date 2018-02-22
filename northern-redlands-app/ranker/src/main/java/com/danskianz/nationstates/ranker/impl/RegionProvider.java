package com.danskianz.nationstates.ranker.impl;

import com.danskianz.nationstates.common.RankerConstants;
import com.github.agadar.nationstates.NationStates;
import com.github.agadar.nationstates.domain.common.CensusScore;
import com.github.agadar.nationstates.domain.nation.Nation;
import com.github.agadar.nationstates.domain.region.Region;
import com.github.agadar.nationstates.enumerator.CensusId;
import com.github.agadar.nationstates.shard.NationShard;
import com.github.agadar.nationstates.shard.RegionShard;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Uses Agadar's NationStates Interface.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public class RegionProvider {
    
    private final Map<String, List<String>> regionNationMap;

    // limit requests to be processed by a daemon thread
    private final ExecutorService censusPool = Executors.newFixedThreadPool(2);

    public RegionProvider() {
        NationStates.setUserAgent(RankerConstants.APP_USER_AGENT);
        regionNationMap = new ConcurrentHashMap<>();
    }

    /**
     *
     * @param region
     * @return
     */
    public Map<String, List<CensusScore>> getRegionCensus(String region) {

        CompletableFuture<Region> promisedLand = CompletableFuture.supplyAsync(
                NationStates.region(region)
                        .shards(RegionShard.NATION_NAMES)::execute, censusPool);

        Region land = promisedLand.join();

        Map<String, List<CensusScore>> regionCensus = land.nationNames
                .stream().collect(Collectors.toMap(
                        nation -> nation,
                        nation -> getNationCensus(nation)));

        regionNationMap.put(region, land.nationNames);

        return regionCensus;
    }

    public List<CensusScore> getNationCensus(String nation) {

        CompletableFuture<Nation> promisedCountry = CompletableFuture
                .supplyAsync(NationStates.nation(nation)
                        .censusIds(CensusId.values())
                        .shards(NationShard.CENSUS)::execute, censusPool);

        Nation country = promisedCountry.join();

        return (country != null)
                ? country.census : Collections.<CensusScore>emptyList();
    }

    public Map<String, List<String>> getRegionAndNationNames() {
        return regionNationMap;
    }

}
