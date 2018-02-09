package com.danskianz.nationstates.ranker.impl;

import com.danskianz.nationstates.common.RankerConstants;
import com.github.agadar.nationstates.NationStates;
import com.github.agadar.nationstates.domain.common.CensusScore;
import com.github.agadar.nationstates.domain.nation.Nation;
import com.github.agadar.nationstates.domain.region.Region;
import com.github.agadar.nationstates.enumerator.CensusId;
import com.github.agadar.nationstates.shard.NationShard;
import com.github.agadar.nationstates.shard.RegionShard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Uses Agadar's NationStates Interface.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public class RegionProvider {
    
    private final Map<String, List<String>> regionNationMap;

    public RegionProvider() {
        NationStates.setUserAgent(RankerConstants.APP_USER_AGENT);
        regionNationMap = new ConcurrentHashMap<>();
    }

    public Map<String, List<CensusScore>> getRegionCensus(String region) {

        Map<String, List<CensusScore>> regionCensus = new HashMap<>();

        List<String> nationNames = new ArrayList<>();
        
        Region land = NationStates.region(region)
                .shards(RegionShard.NATION_NAMES).execute();
        
        land.nationNames.forEach((nation) -> {
            regionCensus.put(nation, getNationCensus(nation));
            nationNames.add(nation);
        });
        
        regionNationMap.put(region, nationNames);

        return regionCensus;
    }

    public List<CensusScore> getNationCensus(String nation) {

        Nation country = NationStates.nation(nation)
                .censusIds(CensusId.values()).shards(NationShard.CENSUS)
                .execute();

        return (country != null)
                ? country.census : Collections.<CensusScore>emptyList();
    }

    public Map<String, List<String>> getRegionAndNationNames() {
        return regionNationMap;
    }

}
