package com.danskianz.nationstates.ranker;

import com.github.agadar.nationstates.NationStates;
import com.github.agadar.nationstates.domain.common.CensusScore;
import com.github.agadar.nationstates.domain.nation.Nation;
import com.github.agadar.nationstates.domain.region.Region;
import com.github.agadar.nationstates.enumerator.CensusId;
import com.github.agadar.nationstates.shard.NationShard;
import com.github.agadar.nationstates.shard.RegionShard;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public RegionProvider() {
        NationStates.setUserAgent(APP_USER_AGENT);
    }

    @Override
    public Map<String, List<CensusScore>> getRegionCensus(String region) {
        Region land = NationStates.region(region)
                .shards(RegionShard.NATION_NAMES)
                .execute();

        List<String> nations = land.nationNames;

        Map<String, List<CensusScore>> regionCensus = new HashMap<>();

        // Sorry, no API abuse
        if (nations.size() < 100) {
            nations.forEach((nation) -> {
                regionCensus.put(nation, getNationCensus(nation));
            });
        }
        return regionCensus;
    }

    @Override
    public List<CensusScore> getNationCensus(String nation) {
        Nation country = NationStates.nation(nation)
                .censusIds(CensusId.values()).shards(NationShard.CENSUS)
                .execute();

        return country.census;
    }
}
