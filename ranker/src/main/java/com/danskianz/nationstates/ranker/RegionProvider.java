package com.danskianz.nationstates.ranker;

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
import java.util.function.Consumer;

/**
 * Uses Agadar's NationStates Interface.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public class RegionProvider {

    private static final String APP_USER_AGENT
            = "Kurwianath-Northern-Redlands-Custom-Ranker";

    private final DailyDumps dailyDump;
    
    private final Map<String, List<String>> regionNationMap;

    public RegionProvider() {
        NationStates.setUserAgent(APP_USER_AGENT);
        dailyDump = new DailyDumps();
        regionNationMap = new ConcurrentHashMap<>();
    }

    public Map<String, List<CensusScore>> getRegionCensus(String region,
            RetrievalMode mode) {

        Map<String, List<CensusScore>> regionCensus = new HashMap<>();

        List<String> nationNames = new ArrayList<>();
        
        Consumer<String> mapNationCensus = (nation) -> {
            regionCensus.put(nation, getNationCensus(nation, mode));
            nationNames.add(nation);
        };

        switch (mode) {
            case DATA_DUMP:
                Region r = dailyDump.getRegion(region);
                r.nationNames.forEach(mapNationCensus);
                break;

            case IMMEDIATE:
                Region land = NationStates.region(region)
                        .shards(RegionShard.NATION_NAMES).execute();
                land.nationNames.forEach(mapNationCensus);
                break;
                
            default:
                throw new AssertionError(mode.name());
        }
        
        regionNationMap.put(region, nationNames);

        return regionCensus;
    }

    public List<CensusScore> getNationCensus(String nation,
            RetrievalMode dataRetrievalMode) {

        Nation country = null;

        switch (dataRetrievalMode) {
            case DATA_DUMP:
                country = dailyDump.getNation(nation);
                break;

            case IMMEDIATE:
                country = NationStates.nation(nation)
                        .censusIds(CensusId.values()).shards(NationShard.CENSUS)
                        .execute();
                break;
            default:
                throw new AssertionError(dataRetrievalMode.name());
        }

        return (country != null)
                ? country.census : Collections.<CensusScore>emptyList();
    }

    public Map<String, List<String>> getRegionAndNationNames() {
        return regionNationMap;
    }

}
