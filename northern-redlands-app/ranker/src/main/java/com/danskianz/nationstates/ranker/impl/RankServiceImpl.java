package com.danskianz.nationstates.ranker.impl;

import com.danskianz.nationstates.common.CalculationMode;
import com.danskianz.nationstates.persistence.DedicatedRankerStore;
import com.danskianz.nationstates.persistence.NationStatesRank;
import com.danskianz.nationstates.persistence.NationStatesRankDao;
import com.danskianz.nationstates.ranker.RankService;
import com.github.agadar.nationstates.domain.common.CensusScore;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
public class RankServiceImpl implements RankService {

    private final RegionProvider regionService;
    
    @Inject
    private @DedicatedRankerStore NationStatesRankDao dao;
    
    public RankServiceImpl() {
        this.regionService = new RegionProvider();
    }

    @Override
    public Map<String, Double> getRegionScores(String region,
            CalculationMode fetch) {

        switch (fetch) {
            case REALTIME:
                Map<String, List<CensusScore>> regionCensus = regionService
                        .getRegionCensus(region);

                return regionCensus.entrySet().stream().collect(Collectors.toMap(
                        nation -> nation.getKey(),
                        nation -> getNationScore(nation.getKey(), fetch)));

            case CACHED:
                Map<String, List<String>> regionNationMap = regionService
                        .getRegionAndNationNames();

                if (!regionNationMap.containsKey(region)) {
                    return getRegionScores(region, CalculationMode.REALTIME);
                }

                List<String> nationList = regionNationMap.get(region);
                return nationList.stream().collect(Collectors.toMap(
                        nation -> nation,
                        nation -> getNationScore(nation, fetch)));
            default:
                throw new AssertionError(fetch.name());

        }

    }

    @Override
    public double getNationScore(String nation, CalculationMode fetch) {
        
        switch (fetch) {
            case REALTIME:
                List<CensusScore> nationCensus = regionService
                        .getNationCensus(nation);
                
                NationStatesRank entity = new NationStatesRank();
                
                entity.setNation(nation);
                entity.setRank(Ranker.rank(nationCensus));
                entity.setRankTimestamp(new Date());
                
                dao.save(entity);
                break;
            case CACHED:
                if (dao.findAllByName(nation).isEmpty()) {
                    return getNationScore(nation, CalculationMode.REALTIME);
                }
                
                break;
            default:
                throw new AssertionError(fetch.name());
            
        }
        
        return dao.findLatestByName(nation).getRank();
    }

}
