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

    private static final int DEFAULT_NUM_OF_NATIONS = 5;

    @Inject
    private RankService scorer;

    @Context
    private UriInfo allUri;

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Double> getTopRankings(String region) {
        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        int nations = Integer.parseInt(params.getFirst("top"));

        Map<String, Double> standings = scorer
                .getRegionScores(region, fetch);

        return standings.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(nations)
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue));
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

        Map<String, Double> standings = scorer
                .getRegionScores(region, fetch);

        return standings.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(nations)
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Double> getNationScore(String nation) {
        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        return Collections.singletonMap(nation, scorer
                .getNationScore(nation, fetch));
    }

}
