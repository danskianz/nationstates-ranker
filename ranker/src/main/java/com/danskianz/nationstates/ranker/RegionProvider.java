package com.danskianz.nationstates.ranker;

import com.github.agadar.nationstates.NationStates;
import com.github.agadar.nationstates.domain.common.CensusScore;
import com.github.agadar.nationstates.domain.nation.Nation;
import com.github.agadar.nationstates.domain.region.Region;
import com.github.agadar.nationstates.enumerator.CensusId;
import com.github.agadar.nationstates.shard.NationShard;
import com.github.agadar.nationstates.shard.RegionShard;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;

/**
 * Uses Agadar's NationStates Interface.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Named
@RequestScoped
@Service
public class RegionProvider implements RegionService {

    private static final String APP_USER_AGENT
            = "Kurwianath-Northern-Redlands-Custom-Ranker";

    private final DailyDumps dailyDump;

    public RegionProvider() {
        NationStates.setUserAgent(APP_USER_AGENT);
        dailyDump = new DailyDumps();
    }

    @Override
    public Map<String, List<CensusScore>> getRegionCensus(String region,
            RetrievalMode mode) {

        Map<String, List<CensusScore>> regionCensus = new HashMap<>();

        Consumer<String> mapNationCensus = (nation) -> {
            regionCensus.put(nation, getNationCensus(nation, mode));
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

        return regionCensus;
    }

    @Override
    public List<CensusScore> getNationCensus(String nation,
            RetrievalMode dataRetrievalMode) {

        Nation country;

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

}
