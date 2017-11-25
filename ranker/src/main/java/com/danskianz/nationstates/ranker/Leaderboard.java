package com.danskianz.nationstates.ranker;

import com.github.agadar.nationstates.domain.common.CensusScore;
import io.swagger.annotations.Api;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * Leaderboard API.
 * 
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Api
@ManagedBean
@Path("leaderboard")
public class Leaderboard {

    @Inject
    private RegionService provider;
    
    @Context
    private UriInfo allUri;

    @GET
    @Path("regions/{region}/elites")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> getTopRankings(
            @PathParam("region") String region) {

        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        Map<String, List<CensusScore>> regionCensus = provider
                .getRegionCensus(region);

        Map<String, Double> standings = getStandings(regionCensus);

        return standings.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(Integer.parseInt(params.getFirst("top")))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
    
    @GET
    @Path("regions/{region}/unspeakables")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> getBottomRankings(
            @PathParam("region") String region) {

        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        Map<String, List<CensusScore>> regionCensus = provider
                .getRegionCensus(region);
        
        Map<String, Double> standings = getStandings(regionCensus);

        return standings.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(Integer.parseInt(params.getFirst("bottom")))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Map<String, Double> getStandings(Map<String,
            List<CensusScore>> regionCensus) {
        
        Map<String, Double> standings = new HashMap<>();
        
        regionCensus.entrySet().forEach((nation) -> {
            standings.put(nation.getKey(), Ranker.rawScore(nation.getValue()));
        });
        
        return standings;
    }
    
    @GET
    @Path("nations/{nation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> getNationScore(
            @PathParam("nation") String nation) {

        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        List<CensusScore> nationCensus = provider.getNationCensus(nation);
        
        return Collections.singletonMap(nation, Ranker.rawScore(nationCensus));
    }
}
