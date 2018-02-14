package com.danskianz.nationstates.ranker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Service that deals with creating ranker reports.
 * 
 * @author Daniel Paul Anzaldo <anye.west@gmail.com>
 */
@Api
@Path("reports")
public interface ReportService {
    
    /**
     * Produces BBCode for easier posting to NationStates RMB.
     * 
     * @param region for which the rankings were performed
     * @return BBCode format of the entire region's stored rankings
     */
    @GET
    @Path("/region/{region}/bbcode")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Produces BBCode for easier posting to NationStates RMB",
            notes = "BBCode format of the entire region's stored rankings.")
    public String getBBCodeReport(
            @ApiParam(value = "NationStates region", required = true)
            @PathParam("region") String region);
    
}
