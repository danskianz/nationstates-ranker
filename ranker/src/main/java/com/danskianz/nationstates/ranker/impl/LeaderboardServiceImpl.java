/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danskianz.nationstates.ranker.impl;

import com.danskianz.nationstates.common.CalculationMode;
import com.danskianz.nationstates.ranker.LeaderboardService;
import com.danskianz.nationstates.ranker.RankService;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import org.jvnet.hk2.annotations.Service;

/**
 * Leaderboard API implementation.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    @Inject
    private RankService scorer;

    @Context
    private UriInfo allUri;

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Double> getAllRankings(String region) {
        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        return getRegionStandings(region, -1, fetch);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Double> getTopRankings(String region) {
        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        int nations = Integer.parseInt(params.getFirst("top"));

        return getRegionStandings(region, nations, fetch);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Double> getBottomRankings(String region) {
        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        int nations = Integer.parseInt(params.getFirst("bottom"));

        return getRegionStandings(region, nations, fetch);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Double> getNationScore(String nation) {
        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        return Collections.singletonMap(nation, scorer.getNationScore(nation, CalculationMode.REALTIME));
    }

    private Map<String, Double> getRegionStandings(String region, int max,
            CalculationMode fetch) {

        Map<String, Double> standings = scorer
                .getRegionScores(region, fetch);

        Stream<Map.Entry<String, Double>> stream = standings.entrySet().stream();

        return (max < 0) ? stream.collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue))
                : stream.limit(max).collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
