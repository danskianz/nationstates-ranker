package com.danskianz.nationstates.ranker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Leaderboard API, complete with Swagger Annotations.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
@Api
@Path("leaderboard")
@Produces(MediaType.APPLICATION_JSON)
public interface LeaderboardService {
    
    /**
     * Get All Nations for a Region
     *
     * @param region
     * @return the list of nations in a region.
     */
    @GET
    @Path("regions/{region}")
    @ApiOperation(value = "Get all nations for a region",
            notes = "The score of all nations in a region.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fetch", value = "urgency of retrieving data",
                allowableValues = "REALTIME, CACHED",
                required = true, dataType = "string", paramType = "query")
    })
    public Map<String, Double> getAllRankings(
            @ApiParam(value = "NationStates region", required = true)
            @PathParam("region") String region);
    
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
        @ApiImplicitParam(name = "fetch", value = "urgency of retrieving data",
                allowableValues = "REALTIME, CACHED",
                required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "top", value = "number of nations to show",
                defaultValue = "5", required = false,
                dataType = "integer", paramType = "query")
    })
    public Map<String, Double> getTopRankings(
            @ApiParam(value = "NationStates region", required = true)
            @PathParam("region") String region);
    
    /**
     * Get Bottom X Nations for a Region
     *
     * @param region
     * @return the bottom n ranking nations in a region. If no query parameter
     * is specified, it returns a default of 5 bottom nations in the list.
     */
    @GET
    @Path("regions/{region}/deplorables")
    @ApiOperation(value = "Get bottom X nations for a region",
            notes = "The bottom n ranking nations in a region. "
            + "If no query parameter is specified, it returns "
            + "a default of 5 bottom nations in the list.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fetch", value = "urgency of retrieving data",
                allowableValues = "REALTIME, CACHED",
                required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "bottom", value = "number of nations to show",
                defaultValue = "5", required = false,
                dataType = "integer", paramType = "query")
    })
    public Map<String, Double> getBottomRankings(
            @ApiParam(value = "NationStates region", required = true)
            @PathParam("region") String region);
    
    /**
     * Get the Realtime Raw Score for a Nation
     *
     * @param nation
     * @return the score of the specified nation
     */
    @GET
    @Path("nations/{nation}")
    @ApiOperation(value = "Get the realtime raw score for a nation",
            notes = "The raw score of a nation.")
    public Map<String, Double> getNationScore(
            @ApiParam(value = "NationStates nation", required = true)
            @PathParam("nation") String nation);
}
