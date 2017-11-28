package com.danskianz.nationstates.ranker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
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
@Produces(MediaType.APPLICATION_JSON)
public class Leaderboard {

    private static final int DEFAULT_NUM_OF_NATIONS = 5;

    @Inject
    private RankService scorer;

    @Context
    private UriInfo allUri;

    /**
     * Get Top X Nations for a Region
     *
     * @param region
     * @return the top n ranking nations in a region. If no query parameter is
     * specified, it returns a default of 5 top nations in the list.
     */
    @GET
    @Path("regions/{region}/elites")
    @ApiOperation(value = "Get top X nations for a region",
            notes = "The top n ranking nations in a region. "
            + "If no query parameter is specified, it returns "
            + "a default of 5 top nations in the list.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "mode", value = "mode of retrieving data",
                allowableValues = "IMMEDIATE, DATA_DUMP",
                required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "fetch", value = "urgency of retrieving data",
                allowableValues = "REALTIME, CACHED",
                required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "top", value = "number of nations to show",
                defaultValue = "5", required = false,
                dataType = "integer", paramType = "query")
    })
    public Map<String, Double> getTopRankings(
            @ApiParam(value = "NationStates region", required = true)
            @PathParam("region") String region) {

        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        RetrievalMode mode = RetrievalMode.valueOf(params.getFirst("mode"));
        
        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        int nations = Integer.parseInt(params.getFirst("top"));

        Map<String, Double> standings = scorer
                .getRegionScores(region, mode, fetch);

        return standings.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(nations)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /**
     * Get Bottom X Nations for a Region
     *
     * @param region
     * @return the bottom n ranking nations in a region. If no query parameter
     * is specified, it returns a default of 5 bottom nations in the list.
     */
    @GET
    @Path("regions/{region}/unspeakables")
    @ApiOperation(value = "Get bottom X nations for a region",
            notes = "The bottom n ranking nations in a region. "
            + "If no query parameter is specified, it returns "
            + "a default of 5 top nations in the list.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "mode", value = "mode of retrieving data",
                allowableValues = "IMMEDIATE, DATA_DUMP",
                required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "fetch", value = "urgency of retrieving data",
                allowableValues = "REALTIME, CACHED",
                required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "bottom", value = "number of nations to show",
                defaultValue = "5", required = false,
                dataType = "integer", paramType = "query")
    })
    public Map<String, Double> getBottomRankings(
            @ApiParam(value = "NationStates region", required = true)
            @PathParam("region") String region) {

        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        RetrievalMode mode = RetrievalMode.valueOf(params.getFirst("mode"));
        
        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        int nations = Integer.parseInt(params.getFirst("bottom"));

        Map<String, Double> standings = scorer
                .getRegionScores(region, mode, fetch);

        return standings.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(nations)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /**
     * Get the Raw Score for a Nation
     *
     * @param nation
     * @return the score of the specified nation
     */
    @GET
    @Path("nations/{nation}")
    @ApiOperation(value = "Get the raw score for a nation",
            notes = "The raw score of a nation.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "mode", value = "mode of retrieving data",
                allowableValues = "IMMEDIATE, DATA_DUMP",
                required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "fetch", value = "urgency of retrieving data",
                allowableValues = "REALTIME, CACHED",
                required = true, dataType = "string", paramType = "query")
    })
    public Map<String, Double> getNationScore(
            @ApiParam(value = "NationStates nation", required = true)
            @PathParam("nation") String nation) {

        MultivaluedMap<String, String> params = allUri.getQueryParameters();

        RetrievalMode mode = RetrievalMode.valueOf(params.getFirst("mode"));
        
        CalculationMode fetch = CalculationMode
                .valueOf(params.getFirst("fetch"));

        return Collections.singletonMap(nation, scorer
                .getNationScore(nation, mode, fetch));
    }
}
