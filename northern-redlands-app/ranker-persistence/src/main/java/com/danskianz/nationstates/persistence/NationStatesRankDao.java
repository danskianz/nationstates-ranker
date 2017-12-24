package com.danskianz.nationstates.persistence;

import java.util.List;

/**
 * Service to persist rank records in historic order.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public interface NationStatesRankDao {

    public void save(NationStatesRank rank);
    
    public List<NationStatesRank> findAll();
    
    public List<NationStatesRank> findAllMostRecent();
    
    public List<NationStatesRank> findAllByName(String nation);
    
    public NationStatesRank findLatestByName(String nation);
    
}
