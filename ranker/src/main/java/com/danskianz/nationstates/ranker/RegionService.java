package com.danskianz.nationstates.ranker;

import com.github.agadar.nationstates.domain.common.CensusScore;
import java.util.List;
import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

/**
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Contract
public interface RegionService {
    
    public Map<String, List<CensusScore>> getRegionCensus(
            String region, RetrievalMode mode);

    public List<CensusScore> getNationCensus(
            String nation, RetrievalMode mode);
    
    public Map<String, List<String>> getRegionAndNationNames();
}
